package com.fpt.swp391.dto;

import com.fpt.swp391.model.Order;
import com.fpt.swp391.model.StatusEnum;
import com.fpt.swp391.model.User;

import java.util.Date;

public class TransactionDto {
    private Long id;
    private StatusEnum status;
    private Order order;
    private User user;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
