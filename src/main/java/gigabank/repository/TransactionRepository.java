package gigabank.repository;

import gigabank.entity.Transaction;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll();

    Transaction findById(long id);

    long save(Transaction transaction);

    void updateById(Transaction transaction);

    void deleteById(long id);
}