package gigabank.service;

import gigabank.entity.BankAccount;
import gigabank.entity.User;
import gigabank.repository.BankAccountRepository;
import gigabank.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    /**
     * Получает список всех пользователей из Repository с их банковскими счетами
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Предоставляет пользователя по его id из Repository с его банковскими счетами
     * @param id Пользователя
     */
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Добавляет нового Пользователя в Repository и создаёт ему Банковский счёт с нулевым балансом
     * @param user новый Пользователь из Controller
     */
    public Long save (User user) {

        final User createdUser = userRepository.save(user);

        // создаёт новомому Пользователю Банковский аккаунт
        // ---------------------------
        BankAccount bankAccount = new BankAccount(
                null,
                new BigDecimal("0.00"),
                createdUser,
                new ArrayList<>()
        );

        bankAccountRepository.save(bankAccount);
        // ---------------------------

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