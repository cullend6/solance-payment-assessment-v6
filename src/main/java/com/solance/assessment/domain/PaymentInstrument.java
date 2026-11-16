package com.solance.assessment.domain;

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
public class PaymentInstrument {

  private UUID id;
  private UUID clientId;
  private String iban;
  private SupportedCurrency currency;
  private BigDecimal balance;
  private PaymentInstrumentStatus status;
  private PaymentInstrumentType type;
}
