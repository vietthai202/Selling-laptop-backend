package com.fpt.swp391.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String phone;
	private Date dateOfBirth;
	@Column(unique = true)
	private String username;
	private String password;
	@Column(unique = true)
	private String email;
	private String address;
	private String image;
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	@OneToMany(mappedBy = "user")
	private Set<Cart> carts;
	@OneToMany(mappedBy = "user")
	private Set<Order> orders;
	@OneToMany(mappedBy = "user")
	private Set<Transaction> transactions;
	@OneToMany(mappedBy = "user")
	private Set<Laptop> laptops;
	@OneToMany(mappedBy = "user")
	private Set<ReceiveAddress> rcaddress;
	@OneToMany(mappedBy = "user")
	private Set<Review> reviews;
}
