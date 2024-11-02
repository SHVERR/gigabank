package gigabank.repository;

import gigabank.entity.TransactionCategory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class AnalyticsRepositoryImpl implements AnalyticsRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(AnalyticsRepositoryImpl.class);

    @Override
    public BigDecimal getLargestTransactionFromBankAccount(Long bankAccountId) {
        return jdbcTemplate.queryForObject("SELECT MAX(value) FROM app_transaction WHERE bank_account_id=?",
                BigDecimal.class, bankAccountId);
    }

    @Override
    public BigDecimal getSmallestTransactionFromBankAccount(Long bankAccountId) {
        return jdbcTemplate.queryForObject("SELECT MIN(value) FROM app_transaction WHERE bank_account_id=?",
                BigDecimal.class, bankAccountId);
    }

    @Override
    public BigDecimal getAverageTransactionFromBankAccount(Long bankAccountId) {
        return jdbcTemplate.queryForObject("SELECT AVG(value) FROM app_transaction WHERE bank_account_id=?",
                BigDecimal.class, bankAccountId);
    }

    @Override
    public BigDecimal getSumTransactionsByCategoryFromBankAccount(Long bankAccountId, TransactionCategory category) {
        String sql = "SELECT SUM(value) FROM app_transaction WHERE bank_account_id=? " +
        "AND category_id = (SELECT category_id FROM transaction_category WHERE category_name = ?)";

        return jdbcTemplate.queryForObject(sql, BigDecimal.class, bankAccountId, category.getName());
    }
}