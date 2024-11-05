package gigabank.integrationtest.service;

import gigabank.entity.User;
import gigabank.service.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Test
    void testUserCRUD() {
        // Create and Read
        final User user = new User(
                null,
                "testFirstName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(18),
                new ArrayList<>());

        Long userId = userService.save(user);
        User createdUser = userService.findById(userId);

        assertEquals("testFirstName", createdUser.getFirstName());

        // Update
        createdUser.setFirstName("testNewFirstName");
        userService.updateById(createdUser.getId(), createdUser);
        createdUser = userService.findById(createdUser.getId());
        String newFirstName = createdUser.getFirstName();

        assertEquals("testNewFirstName", newFirstName);

        // Delete
        userService.deleteById(createdUser.getId());

        assertNull(userService.findById(createdUser.getId()));
    }

    @Test
    void shouldFindUserByFirstName() {
        final User user = new User(
                null,
                "testFirstName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(18),
                new ArrayList<>());

        Long userId = userService.save(user);

        assertEquals("testFirstName", userService.findByFirstName("testFirstName").getFirst().getFirstName());

        userService.deleteById(userId);
    }

    @Test
    void findByFirstNameOrderByBirthDate() {
        final User user1 = new User(
                null,
                "testFirstName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(30),
                new ArrayList<>());

        Long userId1 = userService.save(user1);

        final User user2 = new User(
                null,
                "testFirstName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(18),
                new ArrayList<>());

        Long userId2 = userService.save(user2);

        final User user3 = new User(
                null,
                "testFirstName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(25),
                new ArrayList<>());

        Long userId3 = userService.save(user3);

        final User user4 = new User(
                null,
                "testFirstName",
                "testMiddleName",
                "testLastName",
                "+71234567890",
                LocalDate.now().minusYears(32),
                new ArrayList<>());

        Long userId4 = userService.save(user4);

        List<User> users = userService.findByFirstNameOrderByBirthDate("testFirstName");

        String formattedUsers = users.stream()
                .map(user -> String.format("id: %s, birthDate: %s", user.getId(), user.getBirthDate()))
                .collect(Collectors.joining("\n")); // Объединяем строки с переносом строки

        logger.info("\nСписок пользователей:\n{}", formattedUsers); // \n перед {} добавит перенос строки перед списком


        assertEquals(user4.getBirthDate(), users.getFirst().getBirthDate());

        userService.deleteById(userId1);
        userService.deleteById(userId2);
        userService.deleteById(userId3);
        userService.deleteById(userId4);

        //userService.deleteAll();

    }
}