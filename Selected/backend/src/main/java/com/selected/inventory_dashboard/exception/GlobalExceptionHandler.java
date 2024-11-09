package com.selected.inventory_dashboard.exception;

import com.selected.inventory_dashboard.dtovo.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AlarmThresholdException.class)
    public ResponseEntity<ErrorResponse> handleAlarmThresholdException(AlarmThresholdException e) {
        return badRequestResponseHandler(e);
    }

    @ExceptionHandler(NoVendorDataException.class)
    public ResponseEntity<ErrorResponse> handleNoVendorDataException(NoVendorDataException e) {
        return badRequestResponseHandler(e);
    }

    private ResponseEntity<ErrorResponse> badRequestResponseHandler(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}