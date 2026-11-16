package com.solance.assessment.api;

import com.solance.assessment.api.model.ApiResponse;
import com.solance.assessment.exception.PaymentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(PaymentException.class)
  public ResponseEntity<ApiResponse<Void>> handlePaymentException(PaymentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(ex.getErrorCode().getCode(), ex.getMessage()));
  }

  @ExceptionHandler(UnsupportedOperationException.class)
  public ResponseEntity<ApiResponse<Void>> handleNotImplemented(UnsupportedOperationException ex) {
    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
        .body(ApiResponse.error(501, "Feature not yet implemented"));
  }
}
