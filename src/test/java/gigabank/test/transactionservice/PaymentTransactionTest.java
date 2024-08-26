package gigabank.test.transactionservice;

import gigabank.entity.Transaction;
import gigabank.entity.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static gigabank.test.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class PaymentTransactionTest {
    @BeforeEach
    void initializerTest() {
        initializer();
    }

    @BeforeEach
    void resetBankAccountBalance() {
        bankAccountTest1.setBalance(BigDecimal.ZERO);
    }

    @Test
    void bankAccountBalanceMustDecrease() {
        bankAccountTest1.setBalance(TWENTY_DOLLARS);
        transactionService.paymentTransaction(bankAccountTest1, transaction1);
        BigDecimal newBalance = bankAccountTest1.getBalance();
        assertEquals(TEN_DOLLARS, newBalance);
    }

    @Test
    void failIfBankAccountBalanceZero() {
        boolean successPayment;
        bankAccountTest1.setBalance(BigDecimal.ZERO);
        successPayment = transactionService.paymentTransaction(bankAccountTest1, transaction1);
        assertFalse(successPayment);
    }

    @Test
    void failIfPaymentZero() {
        boolean successPayment;
        Transaction transactionZero = new Transaction(
                "0",
                BigDecimal.ZERO,
                TransactionType.PAYMENT,
                BEAUTY_CATEGORY,
                bankAccountTest1,
                TEN_DAYS_AGO);

        successPayment = transactionService.paymentTransaction(bankAccountTest1, transactionZero);
        assertFalse(successPayment);
    }

    @Test
    void checkCreatedPaymentTransaction() {
        bankAccountTest1.getTransactions().clear();
        bankAccountTest1.setBalance(TWENTY_DOLLARS);
        transactionService.paymentTransaction(bankAccountTest1, transaction1);
        Transaction lastTransaction = bankAccountTest1.getTransactions()
                .getLast();

        assertEquals(TEN_DOLLARS, lastTransaction.getValue());
        assertEquals(TransactionType.PAYMENT, lastTransaction.getType());
        assertEquals("Beauty", lastTransaction.getCategory());
    }

    @Test
    void checkNullInput() {
        boolean successPayment1;
        boolean successPayment2;
        successPayment1 = transactionService.paymentTransaction(bankAccountNull, transaction1);
        successPayment2 = transactionService.paymentTransaction(bankAccountTest1, null);
        assertFalse(successPayment1);
        assertFalse(successPayment2);
    }
}