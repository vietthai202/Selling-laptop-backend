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
@Table(name = "laptops")
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    private String title;
    private String metaTitle;
    private String slug;
    private String summary;
    private String image;
    private String sku;
    private Float price;
    private Float discount;
    private int quantity;
    @OneToMany(mappedBy = "laptop")
    private Set<Metadata> listMetadata;
    @ManyToMany(mappedBy = "laptops")
    private Set<Tag> tags = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    @OneToMany(mappedBy = "laptop")
    private Set<CartItem> cartItems;
    @OneToMany(mappedBy = "laptop")
    private Set<FAPs> listFaps;
    @OneToMany(mappedBy = "laptop")
    private Set<OrderItem> orderItems;
    @OneToMany(mappedBy = "laptop")
    private Set<Review> reviews;
    @OneToMany(mappedBy = "laptop")
    private Set<LaptopImg> listImg;
}
