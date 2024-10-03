package gigabank.service;

import gigabank.dto.BankAccountDTO;
import gigabank.dto.UserDTO;
import gigabank.entity.BankAccount;
import gigabank.entity.User;
import gigabank.repository.BankAccountRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис отвечает за управление счетами, включая создание, удаление и пополнение
 */
@Service
@Data
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    @Deprecated
    private Map<User, List<BankAccount>> userAccounts = new HashMap<>();
    @Deprecated
    private List<BankAccount> bankAccounts = new ArrayList<>();

    public List<BankAccountDTO> findAll() {
        return bankAccountRepository.findAll().stream()
                .map(bankAccount -> new BankAccountDTO().toDTO(bankAccount))
                .toList();
    }

    public BankAccountDTO findById(long id) {
        return new BankAccountDTO().toDTO(bankAccountRepository.findById(id));
    }

    public long create(UserDTO userDTO, BankAccountDTO bankAccountDTO) {
        User user = userDTO.toEntity(userDTO);
        BankAccount bankAccount = bankAccountDTO.toEntity(bankAccountDTO);
        bankAccount.setOwner(user);
        long newBankAccountId = bankAccountRepository.save(bankAccount);
        return newBankAccountId;
    }

    public void updateById(long id, BankAccountDTO bankAccountDTO) {
        bankAccountDTO.setId(id);
        bankAccountRepository.updateById(bankAccountDTO.toEntity(bankAccountDTO));
    }

    public void deleteById(long id) {
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