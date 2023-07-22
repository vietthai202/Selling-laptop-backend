package com.fpt.swp391.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String name;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String description;
    private Long quantity;
    private Date createDate;
    private Boolean status;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "laptop_discount",
            joinColumns = { @JoinColumn(name = "laptop_id") },
            inverseJoinColumns = { @JoinColumn(name = "discount_id") }
    )
    private Set<Laptop> laptops = new HashSet<>();
}
