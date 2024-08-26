package gigabank.service;

import gigabank.entity.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class UserService {
    private final List<User> users;

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserById(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}