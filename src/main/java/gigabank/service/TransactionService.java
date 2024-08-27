package gigabank.service;

import gigabank.entity.BankAccount;
import gigabank.entity.Transaction;
import gigabank.entity.TransactionType;
import gigabank.entity.User;
homework-1
import lombok.Data;
import org.springframework.stereotype.Service;

import lombok.Data;
import org.springframework.stereotype.Service;
master

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.*;

import static gigabank.service.Utils.IS_SUCCESS;
import static gigabank.service.Utils.generateTransactionId;

/**
 * Сервис отвечает за управление платежами и переводами
 */
homework-1
@Service
@Data
public class TransactionService {

    public static final Set<String> TRANSACTION_CATEGORIES = Set.of(
            "Health", "Beauty", "Education", "Deposit", "Transfer");

    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Transaction getTransactionById(String id) {
        return transactions.stream()
                .filter(transaction -> transaction.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public Transaction updateTransaction(String id, Transaction updatedTransaction) {
        Transaction transactionToUpdate = getTransactionById(id);
        transactionToUpdate.setId(updatedTransaction.getId());
        transactionToUpdate.setValue(updatedTransaction.getValue());
        transactionToUpdate.setType(updatedTransaction.getType());
        transactionToUpdate.setCategory(updatedTransaction.getCategory());
        transactionToUpdate.setBankAccount(updatedTransaction.getBankAccount());
        transactionToUpdate.setCreatedDate(updatedTransaction.getCreatedDate());

        return transactionToUpdate;
    }

    public void deleteTransaction(String id) {
        transactions.removeIf(transaction -> transaction.getId().equals(id));
    }


homework-1
@Service
@Data
public class TransactionService {

    public static final Set<String> TRANSACTION_CATEGORIES = Set.of(
            "Health", "Beauty", "Education", "Deposit", "Transfer");

    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Transaction getTransactionById(String id) {
        return transactions.stream()
                .filter(transaction -> transaction.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public Transaction updateTransaction(String id, Transaction updatedTransaction) {
        Transaction transactionToUpdate = getTransactionById(id);
        transactionToUpdate.setId(updatedTransaction.getId());
        transactionToUpdate.setValue(updatedTransaction.getValue());
        transactionToUpdate.setType(updatedTransaction.getType());
        transactionToUpdate.setCategory(updatedTransaction.getCategory());
        transactionToUpdate.setBankAccount(updatedTransaction.getBankAccount());
        transactionToUpdate.setCreatedDate(updatedTransaction.getCreatedDate());

        return transactionToUpdate;
    }

    public void deleteTransaction(String id) {
        transactions.removeIf(transaction -> transaction.getId().equals(id));
    }
master
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
                TransactionType.TRANSFER,
                categoryTransfer,
                fromAccount,
                LocalDateTime.now());

        fromAccount.getTransactions().add(transactionFromAccount);
        transactionFromAccount.setBankAccount(fromAccount);

        toAccount.setBalance(toAccount.getBalance().add(amount));
        Transaction transactionToAccount = new Transaction(
                generateTransactionId(),
                amount,
                TransactionType.TRANSFER,
                categoryTransfer,
                toAccount,
                LocalDateTime.now());

        toAccount.getTransactions().add(transactionToAccount);
        transactionToAccount.setBankAccount(toAccount);
        return IS_SUCCESS;
    }

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