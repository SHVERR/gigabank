package gigabank.controller;

import gigabank.dto.UserDTO;
import gigabank.mapper.UserMapper;
import gigabank.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping()
    public List<UserDTO> getAll() {
        return userService.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable("id") Long id) {
        return userMapper.toDTO(userService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Long> create(@RequestBody @Valid UserDTO userDTO) {
        Long userId = userService.save(userMapper.toEntity(userDTO));
        return ResponseEntity.ok(userId);
    }

    @PatchMapping("/{id}")
    public void updateById(@PathVariable("id") Long id, @RequestBody @Valid UserDTO userDTO) {
        userService.updateById(id, userMapper.toEntity(userDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }
}