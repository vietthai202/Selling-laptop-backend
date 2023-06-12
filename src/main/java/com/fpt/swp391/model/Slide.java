package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "slide")
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "VARCHAR(1000)")
    private String image;
    private String url;
    private boolean status;
}
