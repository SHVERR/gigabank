package gigabank.mapper;

import gigabank.dto.TransactionDTO;
import gigabank.entity.BankAccount;
import gigabank.entity.Transaction;
import gigabank.entity.TransactionCategory;
import gigabank.entity.TransactionType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TransactionMapper implements RowMapper<Transaction> {

    public TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getValue(),
                transaction.getType().getId(),
                transaction.getCategory().getId(),
                transaction.getBankAccount().getId(),
                transaction.getCreatedDate()
        );
    }

    public Transaction toEntity(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction(
                transactionDTO.getId(),
                transactionDTO.getValue(),
                new TransactionType(),
                new TransactionCategory(),
                new BankAccount(),
                transactionDTO.getCreatedDate());

        transaction.getType().setId(transactionDTO.getTypeId());
        transaction.getCategory().setId(transactionDTO.getCategoryId());
        transaction.getBankAccount().setId(transactionDTO.getBankAccountId());

        return transaction;
    }

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