package com.fpt.swp391.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInfo {
    @JsonProperty("id")
    private String id;
    @JsonProperty("arrangementId")
    private String arrangementId;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category")
    private String category;
    @JsonProperty("bookingDate")
    private String bookingDate;
    @JsonProperty("valueDate")
    private String valueDate;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("creditDebitIndicator")
    private String creditDebitIndicator;
    @JsonProperty("runningBalance")
    private String runningBalance;
}
