package gigabank.acceptancetest.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gigabank.dto.BankAccountDTO;
import gigabank.dto.TransactionDTO;
import gigabank.dto.UserDTO;
import gigabank.entity.BankAccount;
import gigabank.entity.Transaction;
import gigabank.entity.User;
import gigabank.service.BankAccountService;
import gigabank.service.TransactionService;
import gigabank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testTransactionCRUD() throws Exception {

        // Create
        UserDTO userDTO = new UserDTO(
                null,
                "testName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(20));

        String serializedUserDTO = objectMapper.writeValueAsString(userDTO);

        MvcResult resultUser = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedUserDTO))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Long userId = objectMapper.readValue(resultUser.getResponse().getContentAsString(), Long.class);
        User user = userService.findById(userId);

        BankAccountDTO bankAccountDTO = new BankAccountDTO(
                null,
                new BigDecimal("12345.99"),
                user.getId());

        String serializedBankAccountDTO = objectMapper.writeValueAsString(bankAccountDTO);

        MvcResult resultBankAccount = mockMvc.perform(post("/bank_accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedBankAccountDTO))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Long bankAccountId = Long.parseLong(resultBankAccount.getResponse().getContentAsString());

        BankAccount bankAccount = bankAccountService.findById(bankAccountId);


        TransactionDTO transactionDTO = new TransactionDTO(
                null,
                new BigDecimal("123.00"),
                1L,
                1L,
                bankAccount.getId(),
                LocalDateTime.now());

        String serializedTransactionDTO = objectMapper.writeValueAsString(transactionDTO);

        MvcResult result = mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedTransactionDTO))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        Long id = Long.parseLong(result.getResponse().getContentAsString());

        Transaction transaction = transactionService.findById(id);
        assertEquals(transaction.getValue(), transactionDTO.getValue());

        // Read
        MvcResult fetchResult = mockMvc.perform(get(String.format("/transactions/%s", id)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        TransactionDTO fetched = objectMapper.readValue(fetchResult.getResponse().getContentAsString(), TransactionDTO.class);
        assertEquals(fetched.getValue(), transactionDTO.getValue());

        // Update
        transactionDTO.setValue(new BigDecimal("555.00"));
        serializedTransactionDTO = objectMapper.writeValueAsString(transactionDTO);

        mockMvc.perform(patch((String.format("/transactions/%s", id)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedTransactionDTO))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        transaction = transactionService.findById(id);
        assertEquals(transaction.getValue(), transactionDTO.getValue());

        // Delete
        mockMvc.perform(delete(String.format("/transactions/%s", id)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        transactionService.deleteById(id);

        assertNull(transactionService.findById(id));

        userService.deleteById(userId);
        bankAccountService.deleteById(bankAccountId);
    }
}