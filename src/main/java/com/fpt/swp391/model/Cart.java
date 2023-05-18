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
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    private String status;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String line;
    private String city;
    private String province;
    private String content;

    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;
    private Date createdAt;
    private Date updatedAt;
}
