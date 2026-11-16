package com.solance.assessment.api;

import com.solance.assessment.api.model.ApiResponse;
import com.solance.assessment.api.model.FeeQuoteRequest;
import com.solance.assessment.api.model.FeeQuoteResponse;
import com.solance.assessment.api.model.PaymentRequest;
import com.solance.assessment.api.model.PaymentResponse;
import com.solance.assessment.service.FeeCalculationService;
import com.solance.assessment.service.PaymentService;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private final PaymentService paymentService;
  private final FeeCalculationService feeCalculationService;

  public PaymentController(
      PaymentService paymentService, FeeCalculationService feeCalculationService) {
    this.paymentService = paymentService;
    this.feeCalculationService = feeCalculationService;
  }

  @PostMapping
  public ResponseEntity<ApiResponse<PaymentResponse>> submitPayment(
      @RequestHeader("X-Client-Id") UUID clientId, @RequestBody PaymentRequest request) {
    PaymentResponse response = paymentService.submitPayment(clientId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
  }

  @PostMapping("/fee-quote")
  public ResponseEntity<ApiResponse<FeeQuoteResponse>> getFeeQuote(
      @RequestBody FeeQuoteRequest request) {
    FeeQuoteResponse response =
        feeCalculationService.calculateFee(
            request.getAmount(), request.getCurrency(), request.getToCurrency());
    return ResponseEntity.ok(ApiResponse.success(response));
  }
}
