package gigabank.integrationtest.service;

import gigabank.dto.UserDTO;
import gigabank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        UserDTO userDTO = new UserDTO(0,"f", "m", "l", LocalDate.now(), new ArrayList<>());
        long userId = userService.create(userDTO);
        UserDTO newUserDTO = userService.findById(userId);

        assertEquals("f", newUserDTO.getFirstName());
    }
}
