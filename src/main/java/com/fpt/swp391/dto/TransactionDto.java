package com.fpt.swp391.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private String content;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Long orderId;
    private Long userId;
}
