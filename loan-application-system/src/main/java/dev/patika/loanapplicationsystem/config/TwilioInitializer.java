package dev.patika.loanapplicationsystem.config;

import com.twilio.Twilio;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import static dev.patika.loanapplicationsystem.config.TwilioConfig.accountSid;
import static dev.patika.loanapplicationsystem.config.TwilioConfig.authToken;

@Configuration
public class TwilioInitializer {
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);

    private final TwilioConfig twilioConfig;

    @Autowired TwilioInitializer(TwilioConfig twilioConfig){
        this.twilioConfig = twilioConfig;
        Twilio.init(accountSid, authToken);
        LOGGER.info("Twilio initialized ... with account sid {} ", accountSid);
    }

}
