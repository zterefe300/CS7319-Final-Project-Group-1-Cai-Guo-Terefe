package com.selected.inventory_dashboard.exception;

public class NoItemDataException extends AppServiceException {
    public NoItemDataException() {
        super("Item does not exist. Please provide an itemId that exists");
    }
}
