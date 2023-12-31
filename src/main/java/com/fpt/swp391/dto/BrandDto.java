package com.fpt.swp391.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {
    private Long id;
    private String name;
    private String description;
    private String image;
    private String slug;
    private Set<LaptopDto> laptopDtos;
}
