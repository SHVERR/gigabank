package gigabank.acceptancetest.contoller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gigabank.controller.UserController;
import gigabank.dto.UserDTO;
import gigabank.entity.User;
import gigabank.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testCreateUser() throws Exception {
        UserDTO userDTO = new UserDTO(0,"f", "m", "l", LocalDate.now(), new ArrayList<>());
        String serializedUserDto = objectMapper.writeValueAsString(userDTO);

        MvcResult result = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serializedUserDto))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        long id = Long.parseLong(result.getResponse().getContentAsString());

        User user = userRepository.findById(id);
        assertEquals(user.getFirstName(), userDTO.getFirstName());

        MvcResult fetchResult = mockMvc.perform(get(String.format("/users/%s", id)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        UserDTO fetched = objectMapper.readValue(fetchResult.getResponse().getContentAsString(), UserDTO.class);
        assertEquals(fetched.getFirstName(), userDTO.getFirstName());
    }


}
