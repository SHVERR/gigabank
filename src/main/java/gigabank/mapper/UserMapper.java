package gigabank.mapper;

import gigabank.dto.UserDTO;
import gigabank.entity.BankAccount;
import gigabank.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class UserMapper implements RowMapper<User> {

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getLastName(),
                user.getPhone(),
                user.getBirthDate());
    }

    public User toEntity(UserDTO userDTO) {
        return new User(userDTO.getId(),
                userDTO.getFirstName(),
                userDTO.getMiddleName(),
                userDTO.getLastName(),
                userDTO.getPhone(),
                userDTO.getBirthDate(),
                null);
    }

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new User(
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("last_name"),
                rs.getString("phone"),
                rs.getDate("birthdate").toLocalDate(),
                null);
    }
}