package gigabank.controller;

import gigabank.dto.UserDTO;
import gigabank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<UserDTO> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") long id) {
        return userService.findById(id);
    }

    @PostMapping()
    public long create(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PatchMapping("/{id}")
    public void updateById(@PathVariable("id") long id, @RequestBody UserDTO userDTO) {
        userService.updateById(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        userService.deleteById(id);
    }
}