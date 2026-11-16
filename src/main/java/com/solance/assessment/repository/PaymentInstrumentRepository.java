package com.solance.assessment.repository;

import com.solance.assessment.domain.PaymentInstrument;
import com.solance.assessment.domain.PaymentInstrumentStatus;
import com.solance.assessment.domain.PaymentInstrumentType;
import com.solance.assessment.domain.SupportedCurrency;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentInstrumentRepository {

  private final Map<UUID, PaymentInstrument> store = new ConcurrentHashMap<>();

  public PaymentInstrumentRepository() {
    seedData();
  }

  private void seedData() {
    UUID clientA = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    // Client A — active EUR account (balance: 1,000.00 in minor units)
    save(
        PaymentInstrument.builder()
            .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
            .clientId(clientA)
            .iban("IE29AIBK93115212345678")
            .currency(SupportedCurrency.EUR)
            .balance(new BigDecimal("100000"))
            .status(PaymentInstrumentStatus.ACTIVE)
            .type(PaymentInstrumentType.IBAN)
            .build());

    // Client A — active GBP account (balance: 500.00 in minor units)
    save(
        PaymentInstrument.builder()
            .id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
            .clientId(clientA)
            .iban("GB29NWBK60161331926819")
            .currency(SupportedCurrency.GBP)
            .balance(new BigDecimal("50000"))
            .status(PaymentInstrumentStatus.ACTIVE)
            .type(PaymentInstrumentType.IBAN)
            .build());

    // Client A — disabled EUR account
    save(
        PaymentInstrument.builder()
            .id(UUID.fromString("33333333-3333-3333-3333-333333333333"))
            .clientId(clientA)
            .iban("IE29AIBK93115200000000")
            .currency(SupportedCurrency.EUR)
            .balance(new BigDecimal("200000"))
            .status(PaymentInstrumentStatus.DISABLED)
            .type(PaymentInstrumentType.IBAN)
            .build());
  }

  public void save(PaymentInstrument instrument) {
    store.put(instrument.getId(), instrument);
  }

  public Optional<PaymentInstrument> findById(UUID id) {
    return Optional.ofNullable(store.get(id));
  }
}
