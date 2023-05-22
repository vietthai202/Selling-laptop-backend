package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    private String name;
    private String content;
    private  String image;
    private Date createdAt;
    private String shortContent;
    private String slug;
    @ManyToOne
    @JoinColumn(name = "blog_category_id", nullable = false)
    private BlogCategory blogCategory;
}
