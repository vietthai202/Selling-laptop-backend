package com.fpt.swp391.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private Float totalPrice;
    private String status;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String line;
    private String city;
    private String province;
    private Set<OrderItemDto>  orderItems;
    private Set<TransactionDto> transactions;
    private Date createdAt;
    private Date updatedAt;
}