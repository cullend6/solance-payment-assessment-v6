package com.solance.assessment.service;

import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

/** FX rate provider backed by a static lookup table. */
@Component
public class StaticFxRateProvider implements FxRateProvider {

  private static final Map<String, Double> RATES =
      Map.of(
          "EUR_GBP", 0.856,
          "GBP_EUR", 1.168,
          "EUR_USD", 1.085,
          "USD_EUR", 0.922,
          "GBP_USD", 1.267,
          "USD_GBP", 0.789);

  @Override
  public Optional<Double> getRate(String fromCurrency, String toCurrency) {
    if (fromCurrency == null || toCurrency == null) {
      return Optional.empty();
    }
    String from = fromCurrency.trim().toUpperCase();
    String to = toCurrency.trim().toUpperCase();
    if (from.equals(to)) {
      return Optional.of(1.0);
    }
    return Optional.ofNullable(RATES.get(from + "_" + to));
  }
}
