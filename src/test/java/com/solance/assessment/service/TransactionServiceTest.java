package com.solance.assessment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.solance.assessment.domain.PaymentScheme;
import com.solance.assessment.domain.SupportedCurrency;
import com.solance.assessment.domain.Transaction;
import com.solance.assessment.domain.TransactionStatus;
import com.solance.assessment.exception.ErrorCode;
import com.solance.assessment.exception.PaymentException;
import com.solance.assessment.repository.TransactionRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionServiceTest {

  private TransactionRepository transactionRepository;
  private TransactionService transactionService;

  @BeforeEach
  void setUp() {
    transactionRepository = new TransactionRepository();
    transactionService = new TransactionService(transactionRepository);
  }

  private Transaction savedTransaction(TransactionStatus status) {
    Transaction transaction =
        Transaction.builder()
            .id(UUID.randomUUID())
            .clientId(UUID.randomUUID())
            .paymentInstrumentId(UUID.randomUUID())
            .amount(new BigDecimal("5000"))
            .currency(SupportedCurrency.EUR)
            .status(status)
            .paymentScheme(PaymentScheme.SEPA)
            .recipientName("Jane Doe")
            .recipientIban("DE89370400440532013000")
            .reference("Invoice 123")
            .createdAt(Instant.now())
            .build();
    transactionRepository.save(transaction);
    return transaction;
  }

  @Test
  @DisplayName("should submit a transaction that is pending approval")
  void shouldSubmitPendingTransaction() {
    Transaction transaction = savedTransaction(TransactionStatus.PENDING_APPROVAL);

    Transaction result =
        transactionService.progressTransaction(transaction.getId(), TransactionStatus.SUBMITTED);

    assertThat(result.getStatus()).isEqualTo(TransactionStatus.SUBMITTED);
  }

  @Test
  @DisplayName("should cancel a transaction that is pending approval")
  void shouldCancelPendingTransaction() {
    Transaction transaction = savedTransaction(TransactionStatus.PENDING_APPROVAL);

    Transaction result =
        transactionService.progressTransaction(transaction.getId(), TransactionStatus.CANCELLED);

    assertThat(result.getStatus()).isEqualTo(TransactionStatus.CANCELLED);
  }

  @Test
  @DisplayName("should start processing a submitted transaction")
  void shouldStartProcessingSubmittedTransaction() {
    Transaction transaction = savedTransaction(TransactionStatus.SUBMITTED);

    Transaction result =
        transactionService.progressTransaction(transaction.getId(), TransactionStatus.PROCESSING);

    assertThat(result.getStatus()).isEqualTo(TransactionStatus.PROCESSING);
  }

  @Test
  @DisplayName("should complete a processing transaction")
  void shouldCompleteProcessingTransaction() {
    Transaction transaction = savedTransaction(TransactionStatus.PROCESSING);

    Transaction result =
        transactionService.progressTransaction(transaction.getId(), TransactionStatus.COMPLETED);

    assertThat(result.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
  }

  @Test
  @DisplayName("should fail a processing transaction")
  void shouldFailProcessingTransaction() {
    Transaction transaction = savedTransaction(TransactionStatus.PROCESSING);

    Transaction result =
        transactionService.progressTransaction(transaction.getId(), TransactionStatus.FAILED);

    assertThat(result.getStatus()).isEqualTo(TransactionStatus.FAILED);
  }

  @Test
  @DisplayName("should not cancel a transaction that was already submitted")
  void shouldNotCancelSubmittedTransaction() {
    Transaction transaction = savedTransaction(TransactionStatus.SUBMITTED);

    try {
      transactionService.progressTransaction(transaction.getId(), TransactionStatus.CANCELLED);
    } catch (PaymentException ex) {
      assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_STATUS_TRANSITION);
    }
  }

  @Test
  @DisplayName("should reject moving a completed transaction back to submitted")
  void shouldRejectCompletedToSubmitted() {
    Transaction transaction = savedTransaction(TransactionStatus.COMPLETED);

    assertThatThrownBy(
            () ->
                transactionService.progressTransaction(
                    transaction.getId(), TransactionStatus.SUBMITTED))
        .isInstanceOf(PaymentException.class)
        .satisfies(
            ex ->
                assertThat(((PaymentException) ex).getErrorCode())
                    .isEqualTo(ErrorCode.INVALID_STATUS_TRANSITION));
  }

  @Test
  @DisplayName("should reject an unknown transaction")
  void shouldRejectUnknownTransaction() {
    assertThatThrownBy(
            () ->
                transactionService.progressTransaction(
                    UUID.randomUUID(), TransactionStatus.SUBMITTED))
        .isInstanceOf(PaymentException.class);
  }
}
