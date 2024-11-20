package com.selected.inventory_dashboard.service.impl;

import com.selected.inventory_dashboard.constants.NotificationMode;
import com.selected.inventory_dashboard.service.interfaces.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${email.mode}")
    private String emailMode;
    private final JavaMailSender mailSender;

    public EmailServiceImpl(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(final String to, final String subject, final String body) {
        if (emailMode.equals(NotificationMode.TEST.name())) {
            logger.debug("Test mode: sent the email below :)");
            logger.info(body);
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
