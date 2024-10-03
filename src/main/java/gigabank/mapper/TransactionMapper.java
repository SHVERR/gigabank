package gigabank.mapper;

import gigabank.entity.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionMapper implements RowMapper<Transaction> {

    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransactionType type = new TransactionType();
        type.setId(rs.getInt("type_id"));

        TransactionCategory category = new TransactionCategory();
        category.setId(rs.getInt("category_id"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(rs.getLong("bank_account_id"));

        return new Transaction(
                rs.getLong("transaction_id"),
                rs.getBigDecimal("value"),
                type,
                category,
                bankAccount,
                rs.getTimestamp("created_date").toLocalDateTime()
        );
    }
}