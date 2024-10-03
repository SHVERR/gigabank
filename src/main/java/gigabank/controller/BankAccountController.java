package gigabank.controller;

import gigabank.dto.BankAccountDTO;
import gigabank.dto.UserDTO;
import gigabank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank_accounts")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @GetMapping()
    public List<BankAccountDTO> getAll() {
        return bankAccountService.findAll();
    }

    @GetMapping("/{id}")
    public BankAccountDTO getById(@PathVariable("id") long id) {
        return bankAccountService.findById(id);
    }

    @PostMapping()
    public long create(@RequestBody UserDTO userDTO, BankAccountDTO bankAccountDTO) {
        return bankAccountService.create(userDTO, bankAccountDTO);
    }

    @PatchMapping("/{id}")
    public void updateById(@PathVariable("id") long id, @RequestBody BankAccountDTO bankAccountDTO) {
        bankAccountService.updateById(id, bankAccountDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") long id) {
        bankAccountService.deleteById(id);
    }
}