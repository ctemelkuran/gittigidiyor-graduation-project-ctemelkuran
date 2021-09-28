package dev.patika.loanapplicationsystem.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@Data
public class TwilioConfig {
    public static final String accountSid = "ACf215e524a5a72eac4371acbb2e2567be";
    public static final String authToken = "133398bc55030c42ec62c87edd321597";
    public static final String trialNumber = "+18646714548";

}
