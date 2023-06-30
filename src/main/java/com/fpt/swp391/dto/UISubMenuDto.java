package com.fpt.swp391.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UISubMenuDto {
    private Long id;
    private String name;
    private String url;
    private int sortOrder;
    private String icon;
    private boolean enable;
    private Long menu_id;
}
