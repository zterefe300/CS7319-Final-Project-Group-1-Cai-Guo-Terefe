package com.selected.inventory_dashboard.exception;

public class NoVendorDataException extends AppServiceException {
    public NoVendorDataException() {
        super(("Vendor is not provided. Please provide a vendor"));
    }

    private NoVendorDataException(String message) {
        super(message);
    }

    static public NoVendorDataException vendorNotFoundException() {
        return new NoVendorDataException("Vendor can't be found. Please provide a vendor that exists or create one and try again.");
    }
}
