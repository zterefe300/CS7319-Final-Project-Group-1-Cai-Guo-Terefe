package com.selected.inventory_dashboard.exception;

public class VendorNameException extends AppServiceException {
    public VendorNameException() {
        super("Must provider a vendor name");
    }
}
