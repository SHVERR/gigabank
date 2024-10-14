package gigabank.controller;

import gigabank.dto.UserDTO;
import gigabank.mapper.UserMapper;
import gigabank.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public UserDTO getById(@PathVariable("id") long id) {
        return userMapper.toDTO(userService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody @Valid UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();

            // Возвращаем 400 Bad Request с ошибками в теле
            return ResponseEntity.badRequest().body(errors);
        }

        long userId = userService.save(userMapper.toEntity(userDTO));
        return ResponseEntity.ok(userId);
    }

    @PatchMapping("/{id}")
    public void updateById(@PathVariable("id") long id, @RequestBody @Valid UserDTO userDTO) {
        userService.updateById(id, userMapper.toEntity(userDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        userService.deleteById(id);
    }
}