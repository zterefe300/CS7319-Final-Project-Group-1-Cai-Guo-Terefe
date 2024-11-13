package com.selected.inventory_dashboard.exception;

public class VendorContactInfoException extends AppServiceException {
    public VendorContactInfoException() {
        super("Must provide a vendor email or phone number");
    }
}
