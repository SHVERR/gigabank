package gigabank.controller;

import gigabank.dto.TransactionDTO;
import gigabank.entity.TransactionCategory;
import gigabank.mapper.TransactionMapper;
import gigabank.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final TransactionMapper transactionMapper;
    private final AnalyticsService analyticsService;

    @GetMapping("largest-transaction-from-bank-account/{bankaccountid}")
    public BigDecimal getLargestTransactionFromBankAccount(@PathVariable("bankaccountid") Long id) {
        return analyticsService.getLargestTransactionFromBankAccount(id);
    }

    @GetMapping("smallest-transaction-from-bank-account/{bankaccountid}")
    public BigDecimal getSmallestTransactionFromBankAccount(@PathVariable("bankaccountid") Long id) {
        return analyticsService.getSmallestTransactionFromBankAccount(id);
    }

    @GetMapping("average-transaction-from-bank-account/{bankaccountid}")
    public BigDecimal getAverageTransactionFromBankAccount(@PathVariable("bankaccountid") Long id) {
        return analyticsService.getAverageTransactionFromBankAccount(id);
    }

    @GetMapping("sum-transactions-by-category-from-bank-account/{bankaccountid}")
    public BigDecimal getSumTransactionsByCategoryFromBankAccount(@PathVariable("bankaccountid") Long id, @RequestParam String category) {
        TransactionCategory transactionCategory = new TransactionCategory(null, category);
        return analyticsService.getSumTransactionsByCategoryFromBankAccount(id, transactionCategory);
    }
}