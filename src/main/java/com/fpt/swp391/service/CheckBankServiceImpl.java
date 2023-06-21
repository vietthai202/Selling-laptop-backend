package com.fpt.swp391.service;

import com.fpt.swp391.model.TransactionInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CheckBankServiceImpl implements CheckBankService{
    private final RestTemplate restTemplate;

    public CheckBankServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<TransactionInfo> getTransactionsFromApi() {
        String apiUrl = "https://mb.olygon.pro/";
        ResponseEntity<TransactionInfo[]> response = restTemplate.getForEntity(apiUrl, TransactionInfo[].class);
        TransactionInfo[] transactionInfos = response.getBody();

        if (transactionInfos != null) {
            return Arrays.asList(transactionInfos);
        } else {
            return Collections.emptyList();
        }

    }
}
