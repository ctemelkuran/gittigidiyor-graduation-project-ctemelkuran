package dev.patika.loanapplicationsystem.service;

import dev.patika.loanapplicationsystem.entity.SmsRequest;

public interface SmsSenderService {
    void sendSms (SmsRequest smsRequest);
}
