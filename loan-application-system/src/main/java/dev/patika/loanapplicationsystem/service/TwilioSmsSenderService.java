package dev.patika.loanapplicationsystem.service;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import dev.patika.loanapplicationsystem.config.TwilioConfig;
import dev.patika.loanapplicationsystem.config.TwilioInitializer;
import dev.patika.loanapplicationsystem.entity.SmsRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static dev.patika.loanapplicationsystem.config.TwilioConfig.trialNumber;

@Service
@RequiredArgsConstructor
public class TwilioSmsSenderService implements SmsSenderService {

    //private final TwilioConfig twilioConfig;
    PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSenderService.class);

    @Override
    public void sendSms(SmsRequest smsRequest) {

        // Parse phone number and convert to Google library PhoneNumber object
        try {
            Phonenumber.PhoneNumber phoneNumberProto = phoneUtil.parse(smsRequest.getPhoneNumber(),"TR");

        if (phoneUtil.isValidNumber(phoneNumberProto)){

            smsRequest.setPhoneNumber(formatPhoneNumber(phoneNumberProto));
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(trialNumber);
            String message = smsRequest.getMessage();
            MessageCreator messageCreator = Message.creator(
                    to,
                    from,
                    message
            );
            messageCreator.create();
            LOGGER.info("Send sms {} ", smsRequest);
        }
        else {
            throw new IllegalArgumentException(
                    "Phone number ["+smsRequest.getPhoneNumber()+"] is not valid.");
        }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

    }

    private String formatPhoneNumber(Phonenumber.PhoneNumber phoneNumber) {
        return phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
    }
}
