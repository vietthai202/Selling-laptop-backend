package com.fpt.swp391.dto;

import com.fpt.swp391.model.UISubmenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UIMenuDto {
    private Long id;
    private String name;
    private String url;
    private String imageUrl;
    private int sortOrder;
    private String icon;
    private boolean enable;
    private Long parent_id;
    private String menuType; // header // slide // footer
    private Set<UISubMenuDto> uiSubmenus;
}
