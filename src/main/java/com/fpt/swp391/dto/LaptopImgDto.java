package com.fpt.swp391.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaptopImgDto {
    private Long id;
    private String image;
    private String url;
    private Long laptop_id;
}
