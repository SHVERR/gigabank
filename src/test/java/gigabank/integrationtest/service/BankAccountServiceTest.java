package gigabank.integrationtest.service;

import gigabank.entity.BankAccount;
import gigabank.entity.User;
import gigabank.service.BankAccountService;
import gigabank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class BankAccountServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;

    @Test
    void testBankAccountCRUD() {
        // Create and Read
        User user = new User(
                null,
                "testName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(18),
                new ArrayList<>());

        user.setId(userService.save(user));

        BankAccount bankAccount = new BankAccount(
                null,
                new BigDecimal("12345.99"),
                user,
                new ArrayList<>());

        Long bankAccountId = bankAccountService.save(bankAccount);

        BankAccount newBankAccount = bankAccountService.findById(bankAccountId);

        assertEquals(user.getId(), newBankAccount.getOwner().getId());
        assertEquals(bankAccount.getBalance(), newBankAccount.getBalance());

        // Update
        newBankAccount.setBalance(new BigDecimal("777.00"));
        bankAccountService.updateById(newBankAccount.getId(), newBankAccount);
        newBankAccount = bankAccountService.findById(newBankAccount.getId());

        assertEquals(new BigDecimal("777.00"), newBankAccount.getBalance());

        // Delete
        bankAccountService.deleteById(newBankAccount.getId());
        userService.deleteById(user.getId());

        assertNull(bankAccountService.findById(newBankAccount.getId()));
        assertNull(userService.findById(user.getId()));
    }
}