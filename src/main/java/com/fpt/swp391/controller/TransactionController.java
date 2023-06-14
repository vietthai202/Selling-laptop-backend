package com.fpt.swp391.controller;

import com.fpt.swp391.model.StatusEnum;
import com.fpt.swp391.model.Transaction;
import com.fpt.swp391.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/listTransaction")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/{transactionId}/status")
    public ResponseEntity<String> updateTransactionStatus(@PathVariable("transactionId") Long transactionId,
                                                          @RequestParam("status") String status) {
        try {
            StatusEnum statusEnum = StatusEnum.valueOf(status.toUpperCase());
            transactionService.updateTransactionStatus(transactionId, statusEnum);
            return ResponseEntity.ok("Transaction status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status provided.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update transaction status.");
        }
    }
}