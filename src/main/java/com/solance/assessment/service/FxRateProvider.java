package com.solance.assessment.service;

import java.util.Optional;

/** Provides foreign exchange rates between currency pairs. */
public interface FxRateProvider {

  /**
   * Get the exchange rate from one currency to another.
   *
   * @param fromCurrency source currency code (e.g., "EUR")
   * @param toCurrency target currency code (e.g., "GBP")
   * @return the rate, or empty if the currency pair is not supported
   */
  Optional<Double> getRate(String fromCurrency, String toCurrency);
}
