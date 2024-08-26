package gigabank.test.bankaccountservice;

import org.junit.jupiter.api.Test;

import static gigabank.test.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertFalse;


class RemoveBankAccountTest {
    @Test
     void bankAccountMustBeRemoved() {
        boolean bankAccountIsExists = true;
        bankAccountService.removeBankAccount(userMaria, bankAccountTest1);
        bankAccountIsExists = userMaria.getBankAccounts().contains(bankAccountTest1);
        assertFalse(bankAccountIsExists);
    }
    @Test
     void bankAccountMustNotRemovedWithUserNull() {
        boolean bankAccountIsExists = true;
        bankAccountIsExists = bankAccountService.removeBankAccount(userNull, bankAccountTest1);
        assertFalse(bankAccountIsExists);
    }

    @Test
     void bankAccountMustNotRemovedWithBankAccountNull() {
        boolean bankAccountIsExists = true;
        bankAccountIsExists = bankAccountService.removeBankAccount(userIvan, bankAccountNull);
        assertFalse(bankAccountIsExists);
    }

    @Test
     void bankAccountMustNotRemovedIfUserDontHaveThisBankAccount() {
        boolean userHaveRemovingBankAccount = true;
        userHaveRemovingBankAccount = bankAccountService.removeBankAccount(userIvan, bankAccountTest3);
        assertFalse(userHaveRemovingBankAccount);
    }
}