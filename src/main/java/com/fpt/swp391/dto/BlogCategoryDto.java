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
public class BlogCategoryDto {
    private Long id;
    private String name;
    private String content;
    private Set<BlogDto> blogDtos;
}
