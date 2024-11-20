package com.selected.inventory_dashboard.service.impl;

import org.springframework.stereotype.Component;

@Component
public class NotificationServiceHelper {
    static String createItemReorderEmailBody(final String vendorName, final String itemName, final Integer reorderQuantity) {
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

    static String createItemReorderSMSBody(final String itemName, final Integer reorderQuantity) {
        return String.format("Reorder Request: Please place a reorder of item %s, with a quantity of %s. " +
                "Confirm receipt of this request to proceed", itemName, reorderQuantity);
    }

    static String createItemAlarmEmailBody(final String vendorName, final String itemName) {
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
    public static String createItemAlarmSMSBody(final String itemName) {
        return String.format("Alarm Notification: Alarm item %s, is low quantity prepare for possible reorder. " +
                "Confirm receipt of this request to proceed", itemName);
    }
}
