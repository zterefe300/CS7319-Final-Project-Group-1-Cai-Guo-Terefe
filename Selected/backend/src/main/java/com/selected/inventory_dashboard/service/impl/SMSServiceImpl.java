package com.selected.inventory_dashboard.service.impl;

import com.selected.inventory_dashboard.service.interfaces.SMSService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl implements SMSService {

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;
    @Value("${twilio.auth.token}")
    private String twilioAuthToken;
    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @PostConstruct
    public void intiTwilio() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    @Override
    public void sendSMS(final String toPhoneNumber, final String message) {
         Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }
}
