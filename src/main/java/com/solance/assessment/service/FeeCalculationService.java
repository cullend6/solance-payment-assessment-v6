package com.solance.assessment.service;

import com.solance.assessment.api.model.FeeQuoteResponse;
import org.springframework.stereotype.Service;

/**
 * Calculates payment fees for domestic and cross-currency (FX) payments.
 *
 * <p>The fee rules are in the README. All amounts are whole minor units (e.g. cents).
 */
@Service
public class FeeCalculationService {

  static final long FLAT_FEE_MINOR_UNITS = 300L;
  static final double FX_FEE_PERCENTAGE = 0.35;
  static final int RATE_VALID_FOR_SECONDS = 20;

  /**
   * Calculate the fee for a payment.
   *
   * @param amount payment amount in minor units
   * @param fromCurrency source currency code (e.g., "EUR")
   * @param toCurrency target currency code, null or blank for a domestic payment
   * @return the fee quote
   * @throws com.solance.assessment.exception.PaymentException for invalid input, with the matching
   *     {@link com.solance.assessment.exception.ErrorCode}
   */
  public FeeQuoteResponse calculateFee(long amount, String fromCurrency, String toCurrency) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
