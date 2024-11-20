package com.unselected.inventory_dashboard.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.unselected.inventory_dashboard.constants.NotificationMode;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class NotificationUtil {
    private static final Logger logger = LoggerFactory.getLogger(NotificationUtil.class);

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;
    @Value("${twilio.auth.token}")
    private String twilioAuthToken;
    @Value("${twilio.phone.number}")
    private static String twilioPhoneNumber;

    @PostConstruct
    public void intiTwilio() {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    public static void sendEmail(final JavaMailSender mailSender,  final String to, final String subject, final String body, final String emailMode) {
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

    public static void sendSMS(final String toPhoneNumber, final String message, final String smsMode) {
        if (smsMode.equals(NotificationMode.TEST.name())) {
            logger.debug("Test mode: sent message below");
            logger.info(message);
            return;
        }

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

    public static String createItemAlarmEmailBody(final String vendorName, final String itemName) {
        return  String.format(
                """
                        Dear %s
                        
                        This is to inform you that the stock for the following item has reached the low quantity and this just an alarm notification not a reorder.
                        
                        Item Name: %s
                        
                        Please ensure timely processing of this reorder to avoid stock outages.
                        
                        If you have any questions, please contact us at appinventory94@gmail.com or +18668351870.
                        
                        Thank you for your continued partnership
                        
                        Best Regards
                        Olif Store.
                        """
                , vendorName, itemName);
    }

    public static String createItemReorderSMSBody(final String itemName, final Integer reorderQuantity) {
        return String.format("Reorder Request: Please place a reorder of item %s, with a quantity of %s. " +
                "Confirm receipt of this request to proceed", itemName, reorderQuantity);
    }

    public static String createItemAlarmSMSBody(final String itemName) {
        return String.format("Alarm Notification: Alarm item %s, is low quantity prepare for possible reorder. " +
                "Confirm receipt of this request to proceed", itemName);
    }
}
