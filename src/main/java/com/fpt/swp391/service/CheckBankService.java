package com.fpt.swp391.service;

import com.fpt.swp391.model.TransactionInfo;

import java.util.List;

public interface CheckBankService {
    List<TransactionInfo> getTransactionsFromApi();
}
