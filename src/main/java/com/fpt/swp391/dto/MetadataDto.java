package com.fpt.swp391.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDto {
    private Long id;
    private String icon;
    private String iconType;
    private String title;
    private String content;
    private Long laptop_id;
    private Long group_id;
}
