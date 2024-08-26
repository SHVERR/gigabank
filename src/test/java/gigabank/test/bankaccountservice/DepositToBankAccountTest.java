package gigabank.test.bankaccountservice;

import gigabank.entity.Transaction;
import gigabank.entity.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static gigabank.test.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class DepositToBankAccountTest {
    @BeforeEach
    void initializerTest() {
        initializer();
    }

    @BeforeEach
    void resetBankAccountBalance() {
        bankAccountTest1.setBalance(BigDecimal.ZERO);
    }

    @Test
    void bankAccountBalanceMustIncrease() {
        bankAccountTest1.setBalance(TEN_DOLLARS);
        bankAccountService.depositToBankAccount(bankAccountTest1, TEN_DOLLARS);
        BigDecimal newBalance = bankAccountTest1.getBalance();
        assertEquals(TWENTY_DOLLARS, newBalance);
    }

    @Test
    void failIfDepozitZero() {
        boolean successDeposit = true;
        successDeposit = bankAccountService.depositToBankAccount(bankAccountTest1, BigDecimal.ZERO);
        assertFalse(successDeposit);
    }

    @Test
    void checkCreatedDepositTransaction() {
        bankAccountService.depositToBankAccount(bankAccountTest1, FIFTEEN_DOLLARS);
        Transaction lastTransaction = bankAccountTest1.getTransactions()
                .get(bankAccountTest1.getTransactions().size() - 1);

        assertEquals(FIFTEEN_DOLLARS, lastTransaction.getValue());
        assertEquals(TransactionType.DEPOSIT, lastTransaction.getType());
        assertEquals("Deposit", lastTransaction.getCategory());
    }

    @Test
    void failIfBankAccountNull() {
        boolean successDeposit = true;
        successDeposit = bankAccountService.depositToBankAccount(bankAccountNull, TEN_DOLLARS);
        assertFalse(successDeposit);
    }
}