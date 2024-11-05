package gigabank.mapper;

import gigabank.dto.UserDTO;
import gigabank.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
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
}