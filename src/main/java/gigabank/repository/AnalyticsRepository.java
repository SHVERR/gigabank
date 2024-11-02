package gigabank.repository;

import gigabank.entity.Transaction;
import gigabank.entity.TransactionCategory;

import java.math.BigDecimal;

public interface AnalyticsRepository {

    BigDecimal getLargestTransactionFromBankAccount(Long bankAccountId);

    BigDecimal getSmallestTransactionFromBankAccount(Long bankAccountId);

    BigDecimal getAverageTransactionFromBankAccount(Long bankAccountId);

    BigDecimal getSumTransactionsByCategoryFromBankAccount(Long bankAccountId, TransactionCategory category);
}