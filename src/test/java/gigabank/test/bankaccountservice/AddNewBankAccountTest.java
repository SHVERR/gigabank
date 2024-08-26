package gigabank.test.bankaccountservice;

import gigabank.entity.BankAccount;
import gigabank.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static gigabank.test.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddNewBankAccountTest {
    @BeforeEach
    void initializerTest() {
        initializer();
    }

    @BeforeEach
    void resetBankAccountBalance() {
        bankAccountTest1.setBalance(BigDecimal.ZERO);
    }

    @Test
    void newBankAccountMustBeAdded() {
        BankAccount newBankAccount = bankAccountService.addNewBankAccount(userIvan);

        //Проверяем, что новый счёт добавился в мапу UserAccounts
        boolean userAccountsMapContainsNewBankAccount = false;
        for (Map.Entry<User, List<BankAccount>> entry : bankAccountService.getUserAccounts().entrySet()) {
            for (BankAccount bankAccount : entry.getValue()) {
                if (bankAccount.getOwner().equals(userIvan)) {
                    userAccountsMapContainsNewBankAccount = true;
                    break;
                }
            }
        }
        assertEquals(userIvan, newBankAccount.getOwner());
        assertTrue(userAccountsMapContainsNewBankAccount);
    }

    @Test
    void oneUserMustHaveMultipleBankAccounts() {
        userIvan.getBankAccounts().clear();

        bankAccountService.addNewBankAccount(userIvan);
        bankAccountService.addNewBankAccount(userIvan);
        bankAccountService.addNewBankAccount(userIvan);

        int countUsers = 0;
        int countBankAccounts = 0;

        for (Map.Entry<User, List<BankAccount>> entry : bankAccountService.getUserAccounts().entrySet()) {
            if (entry.getKey().getFirstName().contains("Ivan")) {
                countUsers++;
            }
        }

        for (Map.Entry<User, List<BankAccount>> entry : bankAccountService.getUserAccounts().entrySet()) {
            for (BankAccount bankAccount : entry.getValue()) {
                if (bankAccount.getOwner().getFirstName().contains("Ivan")) {
                    countBankAccounts++;
                }
            }
        }
        assertEquals(1, countUsers);
        assertEquals(3, countBankAccounts);
    }

    @Test
    void newBankAccountMustNotAddedWithUserNull() {
        BankAccount newBankAccount = bankAccountService.addNewBankAccount(userNull);
        boolean newBankAccountIsNull = newBankAccount == null;
        assertTrue(newBankAccountIsNull);
    }
}