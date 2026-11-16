package com.solance.assessment.exception;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {

  private final ErrorCode errorCode;

  public PaymentException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public PaymentException(ErrorCode errorCode) {
    super(errorCode.getDefaultMessage());
    this.errorCode = errorCode;
  }
}
