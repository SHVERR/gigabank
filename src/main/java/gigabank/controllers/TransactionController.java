package gigabank.controllers;

homework-1
import gigabank.entity.Transaction;
import gigabank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import gigabank.entity.Transaction;
import gigabank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
master

@RestController
@RequestMapping("/transactions")
public class TransactionController {
homework-1

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable("id") String id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        transactionService.addTransaction(transaction);
        return transaction;
    }

    @PatchMapping("/{id}")
    public Transaction updateTransaction(@PathVariable("id") String id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable("id") String id) {
        transactionService.deleteTransaction(id);
    }
}

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable("id") String id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        transactionService.addTransaction(transaction);
        return transaction;
    }

    @PatchMapping("/{id}")
    public Transaction updateTransaction(@PathVariable("id") String id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable("id") String id) {
        transactionService.deleteTransaction(id);
    }
}
master
