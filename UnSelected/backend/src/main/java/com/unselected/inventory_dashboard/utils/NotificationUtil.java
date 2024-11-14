package com.unselected.inventory_dashboard.utils;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class NotificationUtil {
    @Value("${twilio.account.sid}")
    private static String twilioAccountSid;
    @Value("${twilio.auth.token}")
    private static String twilioAuthToken;
    @Value("${twilio.phone.number}")
    private static String twilioPhoneNumber;

    public static void sendEmail(final JavaMailSender mailSender,  final String to, final String subject, final String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public static void sendSMS(final String toPhoneNumber, final String message) {
        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }

    public static String createItemReorderEmailBody(final String vendorName, final String itemName, final Integer reorderQuantity) {
        return  String.format(
                """
                        Dear %s
                        
                        This is to inform you that the stock for the following item has reached the low quantity and we are notifying you for reorder of the following item.
                        
                        Item Name: %s
                        Reorder Quantity: %s
                        
                        Please ensure timely processing of this reorder to avoid stock outages.
                        
                        If you have any questions, please contact us at appinventory94@gmail.com or +18668351870.
                        
                        Thank you for your continued partnership
                        
                        Best Regards
                        Olif Store.
                        """
                , vendorName, itemName, reorderQuantity);
    }

    public static String createItemReorderSMSBody(final String itemName, final Integer reorderQuantity) {
        return String.format("Reorder Request: Please place a reorder of item %s, with a quantity of %s. " +
                "Confirm receipt of this request to proceed", itemName, reorderQuantity);
    }
}
