package com.fpt.swp391.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionWrapper {
    @JsonProperty("transactionInfos")
    private List<TransactionInfo> transactionInfos;
}
