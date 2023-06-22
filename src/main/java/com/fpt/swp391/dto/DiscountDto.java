package com.fpt.swp391.dto;

import com.fpt.swp391.model.Discount;
import com.fpt.swp391.model.Laptop;
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
public class DiscountDto {
    private Long id;
    private String description;
    private Long quantity;
    private Long amount;
    private Date createDate;
    private Date duration;
    private Boolean status;
}
