package gigabank.controller;

import gigabank.dto.BankAccountDTO;
import gigabank.entity.BankAccount;
import gigabank.mapper.BankAccountMapper;
import gigabank.mapper.UserMapper;
import gigabank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bank_accounts")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountMapper bankAccountMapper;
    private final BankAccountService bankAccountService;
    private static final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

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

    @KafkaListener(topics = "user-created-topic", groupId = "consumer-group")
    public Long handleUserCreatedEvent(Long userId) {
        BankAccount bankAccount = bankAccountService.saveForNewUser(userId);
        logger.info("Bank account ID:{} created for User ID:{}", bankAccount.getId(),bankAccount.getOwner().getId());

        return bankAccount.getId();
    }
}