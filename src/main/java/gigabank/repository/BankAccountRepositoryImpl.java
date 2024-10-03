package gigabank.repository;

import gigabank.entity.BankAccount;
import gigabank.entity.User;
import gigabank.mapper.BankAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BankAccountRepositoryImpl implements BankAccountRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Получает из базы данных все банковские счета с их владельцами
     */
    @Override
    public List<BankAccount> findAll() {
        return jdbcTemplate.query("SELECT * FROM app_bank_account", new BankAccountMapper());
    }

/*
    public List<BankAccount> findAll() {
        return jdbcTemplate.query("SELECT ba.bank_account_id, ba.balance, ba.owner_id, " +
                        "json_build_object(" +
                        "'id', u.user_id, 'firstName', u.first_name, 'middleName', u.middle_name, 'lastName', u.last_name) as owner " +
                        "FROM app_bank_account ba " +
                        "LEFT JOIN app_user u ON ba.owner_id = u.user_id " +
                        "GROUP BY ba.bank_account_id, ba.balance, ba.owner_id, u.user_id",
                new BankAccountMapper()
        );
    }
*/

    @Override
    public BankAccount findById(long id) {
        return jdbcTemplate.query("SELECT * FROM app_bank_account WHERE bank_account_id=?",
                new BankAccountMapper(), id).stream().findFirst().orElse(null);
    }

    @Override
    public long save(BankAccount bankAccount) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO app_bank_account (balance, owner_id) VALUES (?, ?) RETURNING bank_account_id",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setBigDecimal(1, bankAccount.getBalance());
            ps.setLong(2,bankAccount.getOwner().getId());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void updateById(BankAccount bankAccount) {
        jdbcTemplate.update("UPDATE app_bank_account SET balance=?, owner_id=? WHERE bank_account_id=?",
                bankAccount.getBalance(),
                bankAccount.getOwner().getId(),
                bankAccount.getId());
    }

    public void deleteById(long id) {
        jdbcTemplate.update("DELETE FROM app_bank_account WHERE bank_account_id=?", id);
    }
}