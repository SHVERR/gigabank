package gigabank.mapper;

import gigabank.entity.BankAccount;
import gigabank.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BankAccountMapper implements RowMapper<BankAccount> {

    public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("owner_id"));
        return new BankAccount(
                rs.getLong("bank_account_id"),
                rs.getBigDecimal("balance"),
                user,
                new ArrayList<>()
        );
    }
}