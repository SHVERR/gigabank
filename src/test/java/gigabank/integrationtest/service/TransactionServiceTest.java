package gigabank.integrationtest.service;

import gigabank.entity.*;
import gigabank.service.BankAccountService;
import gigabank.service.TransactionService;
import gigabank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class TransactionServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    @Test
    void testTransactionCRUD() {
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
                new ArrayList<>()
        );

        bankAccount.setId((bankAccountService.save(bankAccount)));

        Transaction transaction = new Transaction(
                null,
                new BigDecimal("100.00"),
                new TransactionType(1L, "payment"),
                new TransactionCategory(1L, "beauty"),
                bankAccount,
                LocalDateTime.now());

        Long transactionId = transactionService.save(transaction);

        Transaction newTransaction = transactionService.findById(transactionId);

        assertEquals(bankAccount.getId(), newTransaction.getBankAccount().getId());
        assertEquals(transaction.getValue(), newTransaction.getValue());

        // Update
        newTransaction.setValue(new BigDecimal("200.00"));
        transactionService.updateById(newTransaction.getId(), newTransaction);
        newTransaction = transactionService.findById(newTransaction.getId());

        assertEquals(new BigDecimal("200.00"), newTransaction.getValue());

        // Delete
        transactionService.deleteById(newTransaction.getId());
        bankAccountService.deleteById(bankAccount.getId());
        userService.deleteById(user.getId());

        assertNull(transactionService.findById(newTransaction.getId()));
        assertNull(bankAccountService.findById(bankAccount.getId()));
        assertNull(userService.findById(user.getId()));
    }
}