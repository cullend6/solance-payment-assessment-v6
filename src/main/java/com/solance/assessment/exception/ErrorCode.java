package com.solance.assessment.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
  PAYMENT_INSTRUMENT_NOT_FOUND(4400, "Payment instrument not found"),
  INSUFFICIENT_FUNDS(4401, "Insufficient funds"),
  INSTRUMENT_NOT_OWNED(4402, "Payment instrument not owned by client"),
  INVALID_AMOUNT(4403, "Invalid payment amount"),
  INVALID_CURRENCY(4404, "Invalid or unsupported currency"),
  INVALID_STATUS_TRANSITION(4405, "Invalid status transition"),
  TRANSACTION_NOT_FOUND(4406, "Transaction not found"),
  INSTRUMENT_NOT_ACTIVE(4407, "Payment instrument is not active"),
  INVALID_INPUT(4408, "Invalid input");

  private final int code;
  private final String defaultMessage;

  ErrorCode(int code, String defaultMessage) {
    this.code = code;
    this.defaultMessage = defaultMessage;
  }
}
