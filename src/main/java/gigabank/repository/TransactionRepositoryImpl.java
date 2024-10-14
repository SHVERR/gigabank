package gigabank.repository;

import gigabank.entity.Transaction;
import gigabank.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll() {
        return jdbcTemplate.query("SELECT * FROM app_transaction", new TransactionMapper());
    }

    @Override
    public Transaction findById(long id) {
        return jdbcTemplate.query("SELECT * FROM app_transaction WHERE transaction_id=?",
                new TransactionMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public long save(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO app_transaction (value, type_id, category_id, bank_account_id) " +
                            "VALUES (?, ?, ?, ?) RETURNING transaction_id",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setBigDecimal(1, transaction.getValue());
            ps.setLong(2, transaction.getType().getId());
            ps.setLong(3, transaction.getCategory().getId());
            ps.setLong(4, transaction.getBankAccount().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void updateById(Transaction transaction) {
        jdbcTemplate.update("UPDATE app_transaction SET value=?, type_id=?, category_id=?, bank_account_id=? " +
                        "WHERE transaction_id=?",
                transaction.getValue(),
                transaction.getType().getId(),
                transaction.getCategory().getId(),
                transaction.getBankAccount().getId(),
                transaction.getId());
    }

    public void deleteById(long id) {
        jdbcTemplate.update("DELETE FROM app_transaction WHERE transaction_id=?", id);
    }
}