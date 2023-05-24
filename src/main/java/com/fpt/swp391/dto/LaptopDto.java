package com.fpt.swp391.dto;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaptopDto {
    private String userName;
    private String title;
    private String metaTitle;
    private String slug;
    private String summary;
    private String sku;
    private Float price;
    private Float discount;
    private int quantity;
    private Long categoryId;
    private Long brandId;
}

