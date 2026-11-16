package com.solance.assessment.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  private UUID id;
  private UUID clientId;
  private UUID paymentInstrumentId;
  private BigDecimal amount;
  private SupportedCurrency currency;
  private TransactionStatus status;
  private PaymentScheme paymentScheme;
  private String recipientName;
  private String recipientIban;
  private String reference;
  private Instant createdAt;
  private Instant updatedAt;
}
