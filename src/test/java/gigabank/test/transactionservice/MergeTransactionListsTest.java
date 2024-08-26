package gigabank.test.transactionservice;

import gigabank.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import static gigabank.test.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeTransactionListsTest {
    @BeforeEach
    void initializerTest() {
        initializer();
    }

    @BeforeEach
    void resetBankAccountBalance() {
        bankAccountTest1.setBalance(BigDecimal.ZERO);
    }

    @Test
    void mustMergeTwoListsTransactions() {
        BiFunction<List<Transaction>, List<Transaction>, List<Transaction>> biFunction = (l1, l2) -> {
            List<Transaction> result = new ArrayList<>(l1);
            result.addAll(l2);
            return result;
        };

        List<Transaction> list1 = bankAccountTest1.getTransactions();
        int size1 = list1.size();
        List<Transaction> list2 = bankAccountTest2.getTransactions();
        int size2 = list2.size();
        int totalSize = size1 + size2;
        int resultSize = transactionService.mergeTransactionLists(list1, list2, biFunction).size();

        assertEquals(totalSize, resultSize);
    }
}