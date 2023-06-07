package com.fpt.swp391.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FAPsDto {
    private Long id;
    private String title;
    private String content;
    private Long laptop_id;
}
