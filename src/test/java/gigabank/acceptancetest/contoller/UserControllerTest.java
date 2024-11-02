package gigabank.acceptancetest.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gigabank.dto.UserDTO;
import gigabank.entity.User;
import gigabank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testUserCRUD() throws Exception {

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
        assertEquals(user.getFirstName(), userDTO.getFirstName());

        // Read
        MvcResult fetchResult = mockMvc.perform(get(String.format("/users/%s", userId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        UserDTO fetched = objectMapper.readValue(fetchResult.getResponse().getContentAsString(), UserDTO.class);
        assertEquals(fetched.getFirstName(), userDTO.getFirstName());

        // Update
        userDTO.setFirstName("testName2");
        serializedUserDTO = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(patch((String.format("/users/%s", userId)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializedUserDTO))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        user = userService.findById(userId);
        assertEquals(user.getFirstName(), userDTO.getFirstName());

        // Delete
        mockMvc.perform(delete(String.format("/users/%s", userId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        userService.deleteById(userId);

        assertNull(userService.findById(userId));
    }
}