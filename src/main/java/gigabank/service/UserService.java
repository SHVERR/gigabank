package gigabank.service;

import gigabank.entity.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class UserService {
    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public User updateUser(String id, User updatedUser) {
        User userToUpdate = getUserById(id);
        userToUpdate.setId(updatedUser.getId());
        userToUpdate.setFirstName(updatedUser.getFirstName());
        userToUpdate.setMiddleName(updatedUser.getMiddleName());
        userToUpdate.setLastName(updatedUser.getLastName());
        userToUpdate.setBirthDate(updatedUser.getBirthDate());
        userToUpdate.setBankAccounts(updatedUser.getBankAccounts());

        return userToUpdate;
    }

    public void deleteUser(String id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}