# Payment Service - Technical Assessment

## AI Usage Policy

We assume you will use AI tools (Claude, Cursor, Copilot, etc.), we use them too, and we want to hire engineers who use them well. **There is no penalty for using AI heavily. There is a penalty for shipping AI output you didn't review.**

**As part of your submission, include the raw session file from your AI tool.** Commit it to your fork under `ai-session/`. If you used more than one session or more than one tool, include all of them.

We want the verbatim log, not a summary. It will be long, messy, and full of dead ends and wrong turns. That is exactly what we want to see. A tidy write-up of what you did tells us nothing.

> Heads up: if you ask the AI to "export our session" it will usually write you a summary from memory rather than retrieve the actual file. Check what it hands you before you commit it.

Where to find it:

- **Claude Code**: the `.jsonl` for your session lives in `~/.claude/projects/<your-project-directory>/<session-id>.jsonl`
- **Cursor / Copilot / other**: use the tool's built-in chat export

What we look for:

- How you framed the problem and provided context to the model
- How you handled anything that was unclear
- How you verified the model's output instead of just accepting it
- How you iterated when something was wrong

If you prefer not to share your session for privacy reasons, tell us and we'll arrange a 30-minute live pairing session instead.

## Submission

Submit your work as a pull request to this repository. The pull request description is part of the assessment, so fill in every section of the template.

---

## Context

You are joining a fintech company that processes payments for business clients. This is a simplified version of our payment processing service built with **Spring Boot 3** and **Java 21**.

The service handles:
- Payment submissions
- Fee calculations
- Transaction lifecycle management

Treat this like a ticket on a real team. This README is the spec. Like most real tickets it does not cover everything, so where you had to make a call, tell us what you chose and why in your pull request.

Plan for about 90 minutes in total.

## Prerequisites

- **Java 21** (required)
- **Maven 3.8+**
- Your preferred IDE (IntelliJ IDEA, VS Code, etc.)

## Getting Started

```bash
# Verify Java version
java -version   # Must show 21.x.x

# Build the project
mvn clean compile

# Run the tests
mvn test
```

> You should see some tests **failing**. That's expected.

---

## Tasks

### Task 1: Fix the payment service (~20 minutes)

`PaymentService.submitPayment` has defects. Some of the tests in `PaymentServiceTest` fail because of them, but **not every defect has a failing test**.

These are the rules the service must follow:

- A client can only pay from an instrument they own, and only if it is `ACTIVE` and of type `IBAN`.
- The amount must be greater than zero.
- The amount must not exceed the instrument's balance. Paying the full balance is allowed.
- EUR payments are routed via SEPA. Every other currency is routed via SWIFT.

Find the defects, fix them, and list each one in your pull request.

Do not change the tests that are already in `PaymentServiceTest`. You can add new ones. If you think an existing test is wrong, say so in your pull request instead of editing it.

---

### Task 2: Implement fee calculation (~30 minutes)

Implement `FeeCalculationService.calculateFee`.

| Payment type | Fee | The quote must include |
|---|---|---|
| **Domestic** (same currency, or no target currency) | Flat fee of **300 minor units** (e.g., 3.00 EUR) | `feeAmount`, `totalAmount` |
| **FX** (different currencies) | **0.35%** of the amount | `feeAmount`, `totalAmount`, `convertedAmount`, `exchangeRate`, `toCurrency`, `feePercentage`, `rateValidForSeconds`, `rateRefreshedAt` |

- Exchange rates come from `FxRateProvider`, which is already available as a Spring bean.
- `totalAmount` is the amount plus the fee.
- Invalid input must reach the API caller as a 4xx response with the matching `ErrorCode`, never as a 500.
- The endpoint `POST /api/payments/fee-quote` is already wired to this method.
- Write the unit tests you think this needs in `FeeCalculationServiceTest`.

---

### Task 3: Review a teammate's change (~20 minutes)

A teammate implemented `TransactionService.progressTransaction` and its tests in `TransactionServiceTest` with an AI assistant. It was merged without a review, and its tests pass.

This is what the method is meant to do.

Valid state transitions:

```
PENDING_APPROVAL  -->  SUBMITTED
PENDING_APPROVAL  -->  CANCELLED
SUBMITTED         -->  PROCESSING
PROCESSING        -->  COMPLETED
PROCESSING        -->  FAILED
```

- Any other transition must be rejected with a `PaymentException` carrying `ErrorCode.INVALID_STATUS_TRANSITION`.
- An unknown transaction ID must be rejected with `ErrorCode.TRANSACTION_NOT_FOUND`.
- Every status change sets `updatedAt`.

Status updates reach this method from the bank's webhook. The bank can deliver the same update more than once, and updates can arrive out of order.

Review the code and its tests the way you would review a pull request. Write your findings in your own pull request, most serious first, and fix whatever you would not have let through.

---

### Pull request write-up (~15 minutes)

Fill in the pull request template. We read it before we read the code.

---

### Discussion (Whiteboard)

Be prepared to discuss:

1. **Data modeling:** How would you design a DynamoDB table schema for transactions that supports efficient time-range queries per client?
2. **Idempotency:** How would you prevent duplicate payment submissions?
3. **FX flow:** How would you extend this to support a 2-step FX quote flow where the rate expires after 30 seconds?

---

## Project Structure

```
src/main/java/com/solance/assessment/
├── api/                    # REST controllers and request/response models
│   ├── PaymentController       ← endpoints for payment submission and fee quotes
│   ├── GlobalExceptionHandler  ← translates exceptions to API error responses
│   └── model/                  ← request and response DTOs
├── service/                # Business logic
│   ├── PaymentService          ← payment submission (Task 1)
│   ├── FeeCalculationService   ← fee calculation (Task 2)
│   ├── TransactionService      ← transaction lifecycle (Task 3)
│   ├── FxRateProvider          ← interface for exchange rates
│   └── StaticFxRateProvider    ← static rate table implementation
├── domain/                 # Domain entities and enums
│   ├── Transaction, PaymentInstrument
│   └── TransactionStatus, PaymentScheme, SupportedCurrency, etc.
├── repository/             # In-memory repositories (simulate a database)
│   ├── TransactionRepository
│   └── PaymentInstrumentRepository  ← pre-seeded with test data
└── exception/              # Error handling
    ├── PaymentException
    └── ErrorCode
```

## Tech Stack

- Java 21
- Spring Boot 3.3
- Maven
- JUnit 5, Mockito, AssertJ
- Lombok
