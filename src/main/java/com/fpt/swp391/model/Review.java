package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private boolean isEnable;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "laptop_id", nullable = false)
    private Laptop laptop;
}
