package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ui_submenu")
public class UISubmenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String name;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String url;
    private int sortOrder;
    private String icon;
    private boolean enable;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private UIMenu menu;
}
