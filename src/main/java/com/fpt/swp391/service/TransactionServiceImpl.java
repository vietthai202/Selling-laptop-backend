package com.fpt.swp391.service;

import com.fpt.swp391.model.Order;
import com.fpt.swp391.model.StatusEnum;
import com.fpt.swp391.model.Transaction;
import com.fpt.swp391.model.User;
import com.fpt.swp391.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(StatusEnum status, Order order, User user, String content) {
        Transaction transaction = new Transaction();
        transaction.setStatus(status);
        transaction.setOrder(order);
        transaction.setUser(user);
        transaction.setContent(content);
        transaction.setCreatedAt(new Date());
        transaction.setUpdatedAt(new Date());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction findById(Long id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction t = transactionOptional.get();
            return t;
        } else {
            throw new RuntimeException("Cart not found with id: " + id);
        }
    }

    @Override
    public void updateTransactionStatus(Long transactionId, StatusEnum status) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            transaction.setStatus(status);
            transaction.setUpdatedAt(new Date());
            transactionRepository.save(transaction);
        } else {
            throw new RuntimeException("Transaction with ID " + transactionId + " does not exist.");
        }
    }
}