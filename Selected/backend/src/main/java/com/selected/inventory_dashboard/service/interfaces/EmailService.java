package com.selected.inventory_dashboard.service.interfaces;

public interface EmailService {
    void sendEmail(final String to, final String subject, final String body);
}
