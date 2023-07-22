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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String name;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String description;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String slug;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String image;
    @OneToMany(mappedBy = "category")
    private Set<Laptop> laptops;



}
