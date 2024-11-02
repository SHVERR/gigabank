package gigabank.controller;

import gigabank.dto.BankAccountDTO;
import gigabank.mapper.BankAccountMapper;
import gigabank.mapper.UserMapper;
import gigabank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank_accounts")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountMapper bankAccountMapper;
    private final BankAccountService bankAccountService;
    private final UserMapper userMapper;

    @GetMapping()
    public List<BankAccountDTO> getAll() {
        return bankAccountService.findAll().stream()
                .map(bankAccountMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public BankAccountDTO getById(@PathVariable("id") Long id) {
        return bankAccountMapper.toDTO(bankAccountService.findById(id));
    }

    @PostMapping()
    public Long create(@RequestBody BankAccountDTO bankAccountDTO) {

        return bankAccountService.save(bankAccountMapper.toEntity(bankAccountDTO));
    }

    @PatchMapping("/{id}")
    public void updateById(@PathVariable("id") Long id, @RequestBody BankAccountDTO bankAccountDTO) {
        bankAccountService.updateById(id, bankAccountMapper.toEntity(bankAccountDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        bankAccountService.deleteById(id);
    }
}