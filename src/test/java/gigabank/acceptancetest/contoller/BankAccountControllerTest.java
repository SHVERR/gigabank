package gigabank.acceptancetest.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gigabank.dto.BankAccountDTO;
import gigabank.dto.UserDTO;
import gigabank.entity.BankAccount;
import gigabank.entity.User;
import gigabank.service.BankAccountService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BankAccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testBankAccountCRUD() throws Exception {

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
        assertEquals(bankAccount.getBalance(), bankAccountDTO.getBalance());

        // Read
        MvcResult fetchResult = mockMvc.perform(get(String.format("/bank_accounts/%s", bankAccountId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        BankAccountDTO fetched = objectMapper.readValue(fetchResult.getResponse().getContentAsString(), BankAccountDTO.class);
        assertEquals(fetched.getBalance(), bankAccountDTO.getBalance());

        // Update
        bankAccountDTO.setBalance(new BigDecimal("777.00"));
        serializedBankAccountDTO = objectMapper.writeValueAsString(bankAccountDTO);

        mockMvc.perform(patch((String.format("/bank_accounts/%s", bankAccountId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedBankAccountDTO))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        bankAccount = bankAccountService.findById(bankAccountId);
        assertEquals(bankAccount.getBalance(), bankAccountDTO.getBalance());

        // Delete
        mockMvc.perform(delete(String.format("/bank_accounts/%s", bankAccountId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        bankAccountService.deleteById(bankAccountId);

        assertNull(bankAccountService.findById(bankAccountId));

        userService.deleteById(userId);
    }
}