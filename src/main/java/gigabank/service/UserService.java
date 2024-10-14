package gigabank.service;

import gigabank.dto.BankAccountDTO;
import gigabank.dto.UserDTO;
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
    public User findById(long id) {
        return userRepository.findById(id);
    }

    /**
     * Добавляет нового Пользователя в Repository и создаёт ему Банковский счёт с нулевым балансом
     * @param user новый Пользователь из Controller
     */
    public long save (User user) {

        long newUserId = userRepository.save(user);
        user.setId(newUserId);

        // создаёт новомому Пользователю Банковский аккаунт
        // ---------------------------
        BankAccount bankAccount = new BankAccount(
                0,
                new BigDecimal("0.00"),
                user,
                new ArrayList<>()
        );

        bankAccountRepository.save(bankAccount);
        // ---------------------------

        return newUserId;
    }

    public void updateById(long id, User user) {
        user.setId(id);
        userRepository.updateById(user);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}