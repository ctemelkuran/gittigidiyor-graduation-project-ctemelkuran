package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.dto.CustomerDTO;
import dev.patika.loanapplicationsystem.entity.Customer;
import dev.patika.loanapplicationsystem.entity.LoanApplicationLogger;
import dev.patika.loanapplicationsystem.entity.LoanApplicationResult;
import dev.patika.loanapplicationsystem.entity.SmsRequest;
import dev.patika.loanapplicationsystem.entity.enums.LoanResultMessage;
import dev.patika.loanapplicationsystem.exceptions.LoanApplicationNotFoundException;
import dev.patika.loanapplicationsystem.mapper.CustomerMapper;
import dev.patika.loanapplicationsystem.repository.CustomerRepository;
import dev.patika.loanapplicationsystem.repository.LoanApplicationLoggerRepository;
import dev.patika.loanapplicationsystem.repository.LoanApplicationResultRepository;
import dev.patika.loanapplicationsystem.util.ClientRequestInfo;
import dev.patika.loanapplicationsystem.service.validators.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static dev.patika.loanapplicationsystem.util.ErrorMessageConstants.LOAN_APPLICATION_NOT_FOUND;
import static dev.patika.loanapplicationsystem.util.LoanApplicationCalculator.calculateLoanAmount;
import static dev.patika.loanapplicationsystem.util.LoanApplicationCalculator.decideLoanResultMessage;


@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    @Autowired
    private CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final LoanApplicationResultRepository resultRepository;
    @Autowired
    private CustomerMapper customerMapper;
    private final LoanApplicationLoggerRepository loggerRepository;
    private final ClientRequestInfo clientRequestInfo;
    private final TwilioSmsSenderService twilioSmsSenderService;


    /**
     * Loan application is performed with the applyToLoan method.
     * If Customer not exists it is created first, then the result is determined.
     * Result of the loan application is sent to the given phoneNumber in customerDTO
     *
     * @param customerDTO is received from the customer and processed.
     * @return LoanApplicationResult to the endpoint.
     */
    @Transactional
    public LoanApplicationResult applyToLoan(CustomerDTO customerDTO) {
        // check if customer exists
        boolean isExists = customerRepository.existsByIdNumber(customerDTO.getIdNumber());

        LoanApplicationResult loanApplicationResult;
        // if customer not exist with given Id Number save the customer
        if (!isExists) {
            customerService.saveCustomer(customerDTO);
            loanApplicationResult = resultRepository.save(decideLoanApplicationResult(
                    customerRepository.findCustomerByIdNumber(customerDTO.getIdNumber())));

        } else {

            loanApplicationResult = resultRepository.save(decideLoanApplicationResult(
                    customerRepository.findCustomerByIdNumber(customerDTO.getIdNumber())));
        }
        this.saveLoanApplicationToDatabase(loanApplicationResult);

        // TODO sends Sms message to the customer, but commented out due to limited sms sending
        //this.sendSms(createSmsRequest(loanApplicationResult, customerDTO.getPhoneNumber()));

        return loanApplicationResult;

    }

    /**
     * Result of the loan application is decided in this method.
     *
     * @param customer taken as input parameter
     * @return decided result
     */
    public LoanApplicationResult decideLoanApplicationResult(Customer customer) {
        LoanApplicationResult result = new LoanApplicationResult();

        int creditScore = customer.getCustomerCreditScore();

        result.setResultMessage(decideLoanResultMessage(creditScore));
        result.setLoanAmount(calculateLoanAmount(creditScore, customer.getSalary()));
        result.setCustomerIdNumber(customer.getIdNumber());

        return result;
    }

    /**
     * Results of the loan applications can be queried by this method.
     *
     * @param idNumber is required in the sense of business logic
     * @return a certain Customer's {@link LoanApplicationResult}
     */
    public Set<LoanApplicationResult> getApplicationResult(long idNumber) {

        CustomerValidator.validateNationalId(idNumber);

        if (!resultRepository.existsByCustomerIdNumber(idNumber))
            throw new LoanApplicationNotFoundException(String.format(LOAN_APPLICATION_NOT_FOUND, idNumber));

        return resultRepository.findByCustomerIdNumber(idNumber);
    }

    /**
     * Results of the loan applications are logged into the database with
     * saveLoanApplicationToDatabase method after each application.
     *
     * @param result of the application is needed for logging.
     */
    private void saveLoanApplicationToDatabase(LoanApplicationResult result) {
        LoanApplicationLogger logger = new LoanApplicationLogger();

        logger.setCustomerIdNumber(result.getCustomerIdNumber());
        logger.setLoanAmount(result.getLoanAmount());
        logger.setLoanResultMessage(result.getResultMessage());
        logger.setLoanApplicationDate(LocalDate.now());
        logger.setClientUrl(clientRequestInfo.getClientUrl());
        logger.setClientIpAddress(clientRequestInfo.getClientIpAddress());
        logger.setSessionActivityId(clientRequestInfo.getSessionActivityId());

        this.loggerRepository.save(logger);
    }

    /**
     * Loan applications logs can be queried with the loan application date
     *
     * @param applicationDate query date
     * @param pageNumber      number of the quiered page
     * @param pageSize        required size of the page
     * @param pageable        indicates that it is pageable
     * @return page of {@link LoanApplicationLogger} list
     */
    public Page<List<LoanApplicationLogger>> getAllApplicationLogsByDate(String applicationDate, Integer pageNumber, Integer pageSize, Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        CustomerValidator.validateDate(applicationDate, formatter);
        LocalDate applicationDateResult = LocalDate.parse(applicationDate, formatter);

        if (pageNumber != null && pageSize != null) {
            pageable = PageRequest.of(pageNumber, pageSize);
        }

        return this.loggerRepository.findAllApplicationsByDate(applicationDateResult, pageable);
    }

    /**
     * Send the sms by calling {@link TwilioSmsSenderService} "sendSms" method
     *
     * @param smsRequest included the phone number and the message.
     */
    public void sendSms(SmsRequest smsRequest) {
        twilioSmsSenderService.sendSms(smsRequest);
    }

    /**
     * SmsRequest object is created with this method according to result of the application.
     * Depending on the APPROVED or DENIED {@link LoanApplicationResult}
     * 2 different sms message could be created.
     *
     * @param loanApplicationResult contains customer info and the result
     * @param phoneNumber           is used to create {@link SmsRequest}
     * @return SmsRequest which is required to sens Sms.
     */
    public SmsRequest createSmsRequest(LoanApplicationResult loanApplicationResult, String phoneNumber) {
        StringBuilder message = new StringBuilder("Dear customer with National ID: "
                + loanApplicationResult.getCustomerIdNumber() + " your loan application has been "
                + loanApplicationResult.getResultMessage().toString().toLowerCase() + ". ");

        if (loanApplicationResult.getResultMessage().equals(LoanResultMessage.APPROVED)) {
            message.append("Your credit limit is "
                    + loanApplicationResult.getLoanAmount()
                    + " TRY. ");
        }
        message.append("Thank you for choosing our bank. Have great day! ");

        return new SmsRequest(phoneNumber, message.toString());
    }
}
