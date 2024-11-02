package gigabank.service;

import gigabank.entity.Transaction;
import gigabank.entity.User;
import gigabank.repository.TransactionRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.*;

/**
 * Сервис отвечает за управление платежами и переводами
 */
@Service
@Data
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    @Deprecated
    public static final Set<String> TRANSACTION_CATEGORIES = Set.of(
            "Health", "Beauty", "Education", "Deposit", "Transfer");
    @Deprecated
    private final List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id);
    }

    public Long save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void updateById(Long id, Transaction transaction) {
        transaction.setId(id);
        transactionRepository.updateById(transaction);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }


//----------------------------------------------------
/*

    public boolean paymentTransaction(BankAccount bankAccount, Transaction transaction) {
        if (bankAccount == null || transaction == null
                || bankAccount.getBalance().compareTo(transaction.getValue()) < 0
                || transaction.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            return !IS_SUCCESS;
        }
        bankAccount.setBalance(bankAccount.getBalance().subtract(transaction.getValue()));
        transaction.setType(TransactionType.PAYMENT);
        bankAccount.getTransactions().add(transaction);
        transaction.setBankAccount(bankAccount);
        return IS_SUCCESS;
    }

    public boolean transferTransaction(BankAccount fromAccount, BankAccount toAccount, BigDecimal amount) {
        if (fromAccount == null || toAccount == null || amount == null
                || fromAccount.getBalance().compareTo(amount) < 0
                || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return !IS_SUCCESS;
        }

        String categoryTransfer = TRANSACTION_CATEGORIES.stream()
                .filter(c -> c.equals("Transfer"))
                .findFirst()
                .orElse("");

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        Transaction transactionFromAccount = new Transaction(
                generateTransactionId(),
                amount,
                TransactionTypeDeprecated.TRANSFER,
                categoryTransfer,
                fromAccount,
                LocalDateTime.now());

        fromAccount.getTransactions().add(transactionFromAccount);
        transactionFromAccount.setBankAccount(fromAccount);

        toAccount.setBalance(toAccount.getBalance().add(amount));
        Transaction transactionToAccount = new Transaction(
                generateTransactionId(),
                amount,
                TransactionTypeDeprecated.TRANSFER,
                categoryTransfer,
                toAccount,
                LocalDateTime.now());

        toAccount.getTransactions().add(transactionToAccount);
        transactionToAccount.setBankAccount(toAccount);
        return IS_SUCCESS;
    }
*/

    //----Функциональные интерфейсы-----
    public List<Transaction> filterTransactions(User user, Predicate<Transaction> predicate) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        if (user == null || predicate == null) {
            return filteredTransactions;
        }

        filteredTransactions = user.getBankAccounts().stream()
                .flatMap(ba -> ba.getTransactions().stream())
                .filter(predicate)
                .toList();

        return filteredTransactions;
    }

    public List<String> transformTransactions(User user, Function<Transaction, String> function) {
        if (user == null || function == null) {
            return Collections.emptyList();
        }

        return user.getBankAccounts().stream()
                .flatMap(ba -> ba.getTransactions().stream())
                .map(function)
                .toList();
    }

    public void processTransactions(User user, Consumer<Transaction> consumer) {
        if (user == null || consumer == null) {
            return;
        }

        user.getBankAccounts().stream()
                .flatMap(ba -> ba.getTransactions().stream())
                .forEach(consumer);
    }

    public List<Transaction> createTransactionList(Supplier<List<Transaction>> supplier) {
        if (supplier == null) {
            return Collections.emptyList();
        }
        return supplier.get();
    }

    public List<Transaction> mergeTransactionLists(
            List<Transaction> list1, List<Transaction> list2,
            BiFunction<List<Transaction>, List<Transaction>, List<Transaction>> biFunction) {

        if (list1 == null || list2 == null || biFunction == null) {
            return Collections.emptyList();
        }

        return biFunction.apply(list1, list2);
    }
}
