package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ui_menu")
public class UIMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private String imageUrl;
    private int sortOrder;
    private String icon;
    private boolean enable;
    private Long parent_id;
    private String menuType; // header // slide // footer
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UISubmenu> uiSubmenus;
}
