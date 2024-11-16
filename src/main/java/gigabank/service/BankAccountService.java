package gigabank.service;

import gigabank.entity.BankAccount;
import gigabank.entity.User;
import gigabank.exception.UserNotFoundException;
import gigabank.repository.BankAccountRepository;
import gigabank.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис отвечает за управление счетами, включая создание, удаление и пополнение
 */
@Service
@Data
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    public List<BankAccount> findAll() {
        return bankAccountRepository.findAll();
    }

    public BankAccount findById(Long id) {
        return bankAccountRepository.findById(id).orElse(null);
    }

    public Long save(BankAccount bankAccount) {
        // Проверяем, существует ли пользователь
        final User userExists = userRepository.findById(bankAccount.getOwner().getId()).orElse(null);

        if (userExists == null) {
            throw new UserNotFoundException("User with ID " + bankAccount.getOwner().getId() + " does not exist.");
        }

        return bankAccountRepository.save(bankAccount).getId();
    }

    public BankAccount saveForNewUser(Long userId) {
        // Проверяем, существует ли пользователь
        final User userExists = userRepository.findById(userId).orElse(null);

        if (userExists == null) {
            throw new UserNotFoundException("User with ID " + userId + " does not exist.");
        }

        BankAccount bankAccount = new BankAccount(
                null,
                new BigDecimal("0.00"),
                userExists,
                new ArrayList<>()
        );

       return bankAccountRepository.save(bankAccount);
    }

    public void updateById(Long id, BankAccount bankAccount) {
        bankAccount.setId(id);
        bankAccountRepository.save(bankAccount);
    }

    public void deleteById(Long id) {
        bankAccountRepository.deleteById(id);
    }

/*    public boolean depositToBankAccount(BankAccount bankAccount, BigDecimal amount) {
        if (bankAccount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return !IS_SUCCESS;
        }

        bankAccount.setBalance(bankAccount.getBalance().add(amount));

        String categoryDeposit = TRANSACTION_CATEGORIES.stream()
                .filter(c -> c.equals("Deposit"))
                .findFirst()
                .orElse("");

        Transaction transaction = new Transaction(
                generateTransactionId(),
                amount,
                TransactionTypeDeprecated.DEPOSIT,
                categoryDeposit,
                bankAccount,
                LocalDateTime.now());

        bankAccount.getTransactions().add(transaction);
        return IS_SUCCESS;
    }*/
}