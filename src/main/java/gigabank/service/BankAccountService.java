package gigabank.service;

import gigabank.entity.BankAccount;
import gigabank.entity.Transaction;
import gigabank.entity.TransactionType;
import gigabank.entity.User;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gigabank.service.TransactionService.TRANSACTION_CATEGORIES;
import static gigabank.service.Utils.*;

/**
 * Сервис отвечает за управление счетами, включая создание, удаление и пополнение
 */
@Getter
public class BankAccountService {
    private final Map<User, List<BankAccount>> userAccounts = new HashMap<>();

    public BankAccount addNewBankAccount(User user) {
        if (user == null) {
            return null;
        }
        BankAccount bankAccount = new BankAccount(
                generateBankAccountId(),
                BigDecimal.ZERO,
                user,
                new ArrayList<>());
        user.getBankAccounts().add(bankAccount);
        userAccounts.computeIfAbsent(user, k -> user.getBankAccounts());
        return bankAccount;
    }

    public boolean removeBankAccount(User user, BankAccount bankAccount) {
        if (user == null || bankAccount == null ||
                !user.getBankAccounts().contains(bankAccount)) {
            return !IS_SUCCESS;
        }
        user.getBankAccounts().remove(bankAccount);
        return IS_SUCCESS;
    }

    public boolean depositToBankAccount(BankAccount bankAccount, BigDecimal amount) {
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
                TransactionType.DEPOSIT,
                categoryDeposit,
                bankAccount,
                LocalDateTime.now());

        bankAccount.getTransactions().add(transaction);
        return IS_SUCCESS;
    }
}