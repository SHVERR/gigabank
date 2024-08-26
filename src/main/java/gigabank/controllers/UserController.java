package gigabank.controllers;

import gigabank.entity.User;
import gigabank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser() {
        userService.addUser (new User());
    }

    @PatchMapping("/{id}")
    updateUser(@PathVariable("id") String id) {

    }

    @DeleteMapping("/{id}")
    deleteUser(@PathVariable("id") String id) {
    }
}
