package com.solance.assessment.api.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeQuoteResponse {

  private long feeAmount;
  private long totalAmount;
  private Long convertedAmount;
  private String toCurrency;
  private Double exchangeRate;
  private Double feePercentage;
  private Integer rateValidForSeconds;
  private Instant rateRefreshedAt;
}
