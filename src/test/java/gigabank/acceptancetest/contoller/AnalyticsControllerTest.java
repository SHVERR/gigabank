package gigabank.acceptancetest.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gigabank.entity.*;
import gigabank.service.BankAccountService;
import gigabank.service.TransactionService;
import gigabank.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AnalyticsControllerTest {
    Long userId;
    Long bankAccountId;
    Long transactionId1;
    Long transactionId2;
    Long transactionId3;
    Long transactionId4;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        User user = new User(
                null,
                "testName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(18),
                new ArrayList<>());

        user.setId(userService.save(user));
        userId = user.getId();

        BankAccount bankAccount = new BankAccount(
                null,
                new BigDecimal("1000000.00"),
                user,
                new ArrayList<>()
        );

        bankAccount.setId((bankAccountService.save(bankAccount)));
        bankAccountId = bankAccount.getId();

        Transaction transaction1 = new Transaction(
                null,
                new BigDecimal("100.00"),
                new TransactionType(1L, "payment"),
                new TransactionCategory(1L, "beauty"),
                bankAccount,
                LocalDateTime.now().minusDays(3));

        transactionId1 = transactionService.save(transaction1);

        Transaction transaction2 = new Transaction(
                null,
                new BigDecimal("200.00"),
                new TransactionType(1L, "payment"),
                new TransactionCategory(1L, "beauty"),
                bankAccount,
                LocalDateTime.now().minusDays(5));

        transactionId2 = transactionService.save(transaction2);

        Transaction transaction3 = new Transaction(
                null,
                new BigDecimal("300.00"),
                new TransactionType(1L, "payment"),
                new TransactionCategory(1L, "beauty"),
                bankAccount,
                LocalDateTime.now().minusDays(10));

        transactionId3 = transactionService.save(transaction3);

        Transaction transaction4 = new Transaction(
                null,
                new BigDecimal("400.00"),
                new TransactionType(1L, "payment"),
                new TransactionCategory(1L, "beauty"),
                bankAccount,
                LocalDateTime.now().minusDays(15));

        transactionId4 = transactionService.save(transaction4);
    }

    @Test
    void shouldGetLargestTransactionFromBankAccount() throws Exception {

        MvcResult result = mockMvc.perform(get(String.format("/analytics/largest-transaction-from-bank-account/%s", bankAccountId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BigDecimal largestTransactionValue = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);

        assertNotNull(largestTransactionValue);
        assertEquals(0, new BigDecimal("400.00").compareTo(largestTransactionValue));
    }

    @Test
    void shouldGetSmallestTransactionFromBankAccount() throws Exception {

        MvcResult result = mockMvc.perform(get(String.format("/analytics/smallest-transaction-from-bank-account/%s", bankAccountId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BigDecimal smallestTransactionValue = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);

        assertNotNull(smallestTransactionValue);
        assertEquals(0, new BigDecimal("100.00").compareTo(smallestTransactionValue));
    }

    @Test
    void shouldGetAverageTransactionFromBankAccount() throws Exception {

        MvcResult result = mockMvc.perform(get(String.format("/analytics/average-transaction-from-bank-account/%s", bankAccountId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BigDecimal averageTransactionValue = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);


        assertNotNull(averageTransactionValue);
        assertEquals(0, new BigDecimal("250.00").compareTo(averageTransactionValue));    }

    @Test
    void shouldGetSumTransactionsByCategoryFromBankAccount() throws Exception {

        String category = "beauty";

        MvcResult result = mockMvc.perform(get(String.format("/analytics/sum-transactions-by-category-from-bank-account/%s?category=%s", bankAccountId, category)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BigDecimal getSumByCategory = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);

        assertNotNull(getSumByCategory);
        assertEquals(0, new BigDecimal("1000.00").compareTo(getSumByCategory));
    }

    @AfterEach
    void deleteAll() {
        userService.deleteById(userId);
    }
}