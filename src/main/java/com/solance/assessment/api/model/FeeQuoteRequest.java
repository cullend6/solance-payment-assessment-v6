package com.solance.assessment.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeQuoteRequest {

  private Long amount;
  private String currency;
  private String toCurrency;
}
