package com.solance.assessment.api.model;

import com.solance.assessment.domain.SupportedCurrency;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

  private UUID paymentInstrumentId;
  private String recipientName;
  private String recipientIban;
  private BigDecimal amount;
  private SupportedCurrency currency;
  private String reference;
}
