package gigabank.acceptancetest.contoller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gigabank.entity.*;
import gigabank.service.BankAccountService;
import gigabank.service.TransactionService;
import gigabank.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AnalyticsControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsControllerTest.class);

    Long userId;
    Long bankAccountId;
    Transaction transaction1;
    Transaction transaction2;
    Transaction transaction3;
    Transaction transaction4;
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

        transaction1 = new Transaction(
                null,
                new BigDecimal("100.00"),
                new TransactionType(1L, "payment"),
                new TransactionCategory(1L, "beauty"),
                bankAccount,
                LocalDateTime.now().minusYears(3));

        transactionId1 = transactionService.save(transaction1);

        transaction2 = new Transaction(
                null,
                new BigDecimal("200.00"),
                new TransactionType(1L, "payment"),
                new TransactionCategory(1L, "beauty"),
                bankAccount,
                LocalDateTime.now().minusDays(5));

        transactionId2 = transactionService.save(transaction2);

        transaction3 = new Transaction(
                null,
                new BigDecimal("300.00"),
                new TransactionType(1L, "payment"),
                new TransactionCategory(1L, "beauty"),
                bankAccount,
                LocalDateTime.now().minusDays(10));

        transactionId3 = transactionService.save(transaction3);

        transaction4 = new Transaction(
                null,
                new BigDecimal("400.00"),
                new TransactionType(1L, "payment"),
                new TransactionCategory(1L, "beauty"),
                bankAccount,
                LocalDateTime.now().minusDays(15));

        transactionId4 = transactionService.save(transaction4);
    }

    @Test
    void shouldGetLargestTransactionByBankAccountId() throws Exception {

        MvcResult result = mockMvc.perform(get("/analytics/largest-transaction-from-bank-account/{bankaccountid}", bankAccountId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BigDecimal largestTransactionValue = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);

        assertNotNull(largestTransactionValue);
        assertEquals(0, new BigDecimal("400.00").compareTo(largestTransactionValue));
    }

    @Test
    void shouldGetSmallestTransactionByBankAccountId() throws Exception {

        MvcResult result = mockMvc.perform(get("/analytics/smallest-transaction-from-bank-account/{bankaccountid}", bankAccountId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BigDecimal smallestTransactionValue = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);

        assertNotNull(smallestTransactionValue);
        assertEquals(0, new BigDecimal("100.00").compareTo(smallestTransactionValue));
    }

    @Test
    void shouldGetAverageTransactionByBankAccountId() throws Exception {

        MvcResult result = mockMvc.perform(get("/analytics/average-transaction-from-bank-account/{bankaccountid}", bankAccountId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BigDecimal averageTransactionValue = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);


        assertNotNull(averageTransactionValue);
        assertEquals(0, new BigDecimal("250.00").compareTo(averageTransactionValue));
    }

    @Test
    void shouldGetSumTransactionsByBankAccountIdAndByCategory() throws Exception {

        String categoryName = "beauty";

        MvcResult result = mockMvc.perform(get("/analytics/sum-transactions-by-bank-account-id-and-category-name/{bankaccountid}", bankAccountId)
                        .param("category", categoryName))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BigDecimal sum = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);

        assertNotNull(sum);
        assertEquals(0, new BigDecimal("1000.00").compareTo(sum));
    }

    @Test
    void getTransactionValuesByBankAccountIdAndRangeDates() throws Exception {

        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        MvcResult result = mockMvc.perform(get("/analytics/transaction-values-by-bank-account-id-and-range-dates/{bank-account-id}", bankAccountId)
                        .param("start-date", String.valueOf(startDate))
                        .param("end-date", String.valueOf(endDate)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<BigDecimal> transactionValues = List.of(transaction1.getValue(), transaction2.getValue(), transaction3.getValue(), transaction4.getValue());

        List<BigDecimal> createdTransactionValues = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });

        String formattedValues = createdTransactionValues.stream()
                .map(value -> String.format("value: %s", value))
                .collect(Collectors.joining("\n")); // Объединяем строки с переносом строки

        logger.info("\nTransaction values:\n{}", formattedValues); // \n перед {} добавит перенос строки перед списком

        assertNotNull(createdTransactionValues);
        assertIterableEquals(transactionValues, createdTransactionValues);
    }

    @AfterEach
    void deleteUser() {
        userService.deleteById(userId);
    }
}