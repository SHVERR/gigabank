package gigabank.controllers;

import gigabank.entity.BankAccount;
import gigabank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank_accounts")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @GetMapping
    public List<BankAccount> getBankAccounts() {
        return bankAccountService.getBankAccounts();
    }

    @GetMapping("/{id}")
    public BankAccount getBankAccount(@PathVariable("id") String id) {
        return bankAccountService.getBankAccountById(id);
    }

    @PostMapping
    public BankAccount createBankAccount(@RequestBody BankAccount bankAccount) {
        return bankAccountService.addBankAccount(bankAccount);
    }

    @PatchMapping("/{id}")
    public BankAccount updateBankAccount(@PathVariable("id") String id, @RequestBody BankAccount bankAccount) {
        return bankAccountService.updateBankAccount(id, bankAccount);
    }

    @DeleteMapping("/{id}")
    public void deleteBankAccount(@PathVariable("id") String id) {
        bankAccountService.deleteBankAccount(id);
    }
}
