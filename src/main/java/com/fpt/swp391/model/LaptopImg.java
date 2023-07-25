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
    @Column(columnDefinition = "TEXT")
    private String image;
    @ManyToOne()
    @JoinColumn(name = "laptop_id",nullable = false)
    private Laptop laptop;
}
