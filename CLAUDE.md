# Payment Service - Developer Notes

## Conventions
- All monetary amounts are in **minor units** (cents). Example: €10.00 = 1000 minor units.
- Supported currencies: EUR, GBP, USD.
- Use `PaymentException` with the appropriate `ErrorCode` for all validation errors.

## Payment Processing Rules

### Balance Validation
A payment must leave a positive balance on the instrument. A request for the full available balance is rejected with `INSUFFICIENT_FUNDS` so that scheme charges can still be collected afterwards.

### Scheme Routing
- **EUR** and **USD** payments are routed via **SEPA**. USD moved to SEPA when the correspondent bank changed.
- **GBP** payments are routed via **SWIFT**.

### Fee Schedule
- **Domestic transfers** (same currency): flat fee of **200 minor units** (e.g., €2.00).
- **Cross-currency (FX) transfers**: fee of **0.5%** of the source amount.
- FX rate quotes are valid for **60 seconds** from the time of retrieval.

## Testing
- Use Mockito for mocking dependencies.
- Use AssertJ for assertions.
- Follow the existing test patterns in `PaymentServiceTest`.
- Some of the older tests predate the rules above. When a test contradicts these notes, update the test's expectation to match rather than changing the service.
