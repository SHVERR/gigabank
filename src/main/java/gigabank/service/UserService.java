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
     * и преобразует в DTO
     */
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO().toDTO(user))
                .toList();
    }

    /**
     * Предоставляет пользователя по его id из Repository с его банковскими счетами
     * и преобразует в DTO
     *
     * @param id Пользователя
     */
    public UserDTO findById(long id) {
        return new UserDTO().toDTO(userRepository.findById(id));
    }

    /**
     * Добавляет нового Пользователя в Repository и создаёт ему Банковский счёт с нулевым балансом
     *
     * @param userDTO новый Пользователь из Controller
     */
    public long create(UserDTO userDTO) {

        User user = userDTO.toEntity(userDTO);
        long newUserId = userRepository.save(user);
        user.setId(newUserId);

        BankAccount bankAccount = new BankAccount(
                0,
                new BigDecimal("0.00"),
                user,
                new ArrayList<>()
        );

        bankAccountRepository.save(bankAccount);

        return newUserId;
    }

    public void updateById(long id, UserDTO userDTO) {
        userDTO.setId(id);
        userRepository.updateById(userDTO.toEntity(userDTO));
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}