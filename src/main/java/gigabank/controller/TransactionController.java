package gigabank.controller;

import gigabank.dto.TransactionDTO;
import gigabank.mapper.TransactionMapper;
import gigabank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;

    @GetMapping()
    public List<TransactionDTO> getAll() {
        return transactionService.findAll().stream()
                .map(transactionMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public TransactionDTO getById(@PathVariable("id") Long id) {
        return transactionMapper.toDTO(transactionService.findById(id));
    }

    @PostMapping()
    public Long create(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.save(transactionMapper.toEntity(transactionDTO));
    }

    @PatchMapping("/{id}")
    public void updateById(@PathVariable("id") Long id, @RequestBody TransactionDTO transactionDTO) {
        transactionService.updateById(id, transactionMapper.toEntity(transactionDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        transactionService.deleteById(id);
    }
}