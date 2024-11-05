package com.selected.inventory_dashboard.exception;

public class AlarmThresholdException extends AppServiceException {
    public AlarmThresholdException() {
        super("Alarm threshold should always be above quantity threshold");
    }
}
