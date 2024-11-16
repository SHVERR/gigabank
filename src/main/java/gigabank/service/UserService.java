package gigabank.service;

import gigabank.entity.User;
import gigabank.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, Long> kafkaTemplate;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Long save(User user) {
        final User createdUser = userRepository.save(user);
        kafkaTemplate.send("user-created-topic", createdUser.getId());

        return createdUser.getId();
    }

    public void updateById(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public List<User> findByFirstNameOrderByBirthDate(String firstName) {
        return userRepository.findByFirstNameOrderByBirthDate(firstName);
    }
}