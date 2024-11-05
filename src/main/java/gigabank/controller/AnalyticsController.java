package gigabank.controller;

import gigabank.dto.TransactionAggregationRequestDTO;
import gigabank.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
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

    @GetMapping("sum-transactions-by-bank-account-id-and-category-name/{bankaccountid}")
    public BigDecimal getSumTransactionsByBankAccountIdAndCategoryName(@PathVariable("bankaccountid") Long id,
                                                                       @RequestParam("category") String categoryName) {
        return analyticsService.getSumTransactionsByBankAccountIdAndCategoryName(id, categoryName);
    }

    @PostMapping("/transactions/aggregate")
    public List<BigDecimal> aggregateTransactions(@RequestBody TransactionAggregationRequestDTO request) {
        return analyticsService.aggregateTransactions(request);
    }
}