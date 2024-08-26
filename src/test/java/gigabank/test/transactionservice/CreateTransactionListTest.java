package gigabank.test.transactionservice;

import gigabank.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import static gigabank.test.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateTransactionListTest {
    @BeforeEach
    void initializerTest() {
        initializer();
    }

    @BeforeEach
    void resetBankAccountBalance() {
        bankAccountTest1.setBalance(BigDecimal.ZERO);
    }

    @Test
    void mustReturnListTransactions() {
        Supplier<List<Transaction>> supplier = () -> bankAccountTest1.getTransactions();

        List<Transaction> listTransactions = transactionService.createTransactionList(supplier);

        boolean listEquals = bankAccountTest1.getTransactions().equals(listTransactions);

        assertTrue(listEquals);
    }

    @Test
    void mustGetEmptyListTransactions() {
        Supplier<List<Transaction>> supplier = null;
        List<Transaction> listTransactions = transactionService.createTransactionList(supplier);
        assertTrue(listTransactions.isEmpty());
    }
}