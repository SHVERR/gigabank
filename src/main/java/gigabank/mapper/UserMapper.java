package gigabank.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gigabank.entity.BankAccount;
import gigabank.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User(
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("last_name"),
                rs.getDate("birthdate").toLocalDate(),
                new ArrayList<>()
        );
// Преобразование JSON массива bank_accounts в List<BankAccount>
        String bankAccountsJson = rs.getString("bank_accounts");

        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            bankAccounts = new ObjectMapper().readValue(bankAccountsJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        user.setBankAccounts(bankAccounts);

        return user;
    }
}