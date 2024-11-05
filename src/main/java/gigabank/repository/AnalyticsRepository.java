package gigabank.repository;

import gigabank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AnalyticsRepository extends JpaRepository<Transaction, Long>, CustomAnalyticsRepository {

    @Query("SELECT MAX(t.value) FROM Transaction t WHERE t.bankAccount.id = :bankAccountId")
    BigDecimal getLargestTransactionByBankAccountId(Long bankAccountId);

    @Query("SELECT MIN(t.value) FROM Transaction t WHERE t.bankAccount.id = :bankAccountId")
    BigDecimal getSmallestTransactionByBankAccountId(Long bankAccountId);

    @Query("SELECT AVG(t.value) FROM Transaction t WHERE t.bankAccount.id = :bankAccountId")
    BigDecimal getAverageTransactionByBankAccountId(Long bankAccountId);

    @Query("SELECT SUM(t.value) FROM Transaction t " +
            "WHERE t.bankAccount.id = :bankAccountId AND t.category.name = :categoryName")
    BigDecimal getSumTransactionsByBankAccountIdAndCategoryName(Long bankAccountId, String categoryName);
}