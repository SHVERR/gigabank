package gigabank.mapper;

import gigabank.dto.BankAccountDTO;
import gigabank.entity.BankAccount;
import gigabank.entity.Transaction;
import gigabank.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class BankAccountMapper implements RowMapper<BankAccount> {

    public BankAccountDTO toDTO(BankAccount bankAccount) {
        return new BankAccountDTO(
                bankAccount.getId(),
                bankAccount.getBalance(),
                bankAccount.getOwner().getId(),
                bankAccount.getTransactions().stream()
                        .map(Transaction::getId)
                        .toList()
        );
    }

    public BankAccount toEntity(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount(
                bankAccountDTO.getId(),
                bankAccountDTO.getBalance(),
                new User(),
                new ArrayList<>());

        bankAccount.getOwner().setId(bankAccountDTO.getOwnerId());

        return bankAccount;
    }


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