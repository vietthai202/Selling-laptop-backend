package com.fpt.swp391.service;

import com.fpt.swp391.model.Order;
import com.fpt.swp391.model.StatusEnum;
import com.fpt.swp391.model.Transaction;
import com.fpt.swp391.model.User;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(StatusEnum status, Order order, User user, String content);

    List<Transaction> getAllTransactions();

    Transaction findById(Long id);

    void updateTransactionStatus(Long transactionId, StatusEnum status);
}