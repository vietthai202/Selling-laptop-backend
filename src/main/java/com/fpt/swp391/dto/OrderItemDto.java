package com.fpt.swp391.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Long id;
    private Long order_id;
    private Long laptop_id;
    private String sku;
    private Float price;
    private Float discount;
    private int quantity;
    private String active;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
