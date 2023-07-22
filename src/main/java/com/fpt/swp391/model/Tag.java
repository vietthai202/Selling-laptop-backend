package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String name;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String slug;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "laptop_tag",
            joinColumns = { @JoinColumn(name = "laptop_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    Set<Laptop> laptops = new HashSet<>();
}
