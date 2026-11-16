package com.solance.assessment.repository;

import com.solance.assessment.domain.Transaction;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {

  private final Map<UUID, Transaction> store = new ConcurrentHashMap<>();

  public void save(Transaction transaction) {
    store.put(transaction.getId(), transaction);
  }

  public Optional<Transaction> findById(UUID id) {
    return Optional.ofNullable(store.get(id));
  }

  public List<Transaction> findByClientId(UUID clientId) {
    return store.values().stream()
        .filter(tx -> tx.getClientId().equals(clientId))
        .sorted(Comparator.comparing(Transaction::getCreatedAt).reversed())
        .toList();
  }
}
