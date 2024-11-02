package gigabank.integrationtest.service;

import gigabank.entity.User;
import gigabank.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
//@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testUserCRUD() {
        // Create and Read
        User user = new User(
                null,
                "testName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(18),
                new ArrayList<>());

        Long userId = userService.save(user);
        User newUser = userService.findById(userId);

        assertEquals("testName", newUser.getFirstName());

        // Update
        newUser.setFirstName("testNewFirstName");
        userService.updateById(newUser.getId(), newUser);
        newUser = userService.findById(newUser.getId());
        String newFirstName = newUser.getFirstName();

        assertEquals("testNewFirstName", newFirstName);

        // Delete
        userService.deleteById(newUser.getId());

        assertNull(userService.findById(newUser.getId()));
    }
}