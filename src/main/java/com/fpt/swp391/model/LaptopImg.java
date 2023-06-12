package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laptop_images")
public class LaptopImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String image;
    private String url;
    @ManyToOne()
    @JoinColumn(name = "laptop_id",nullable = false)
    private Laptop laptop;
}
