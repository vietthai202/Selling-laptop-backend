package com.fpt.swp391.repository;

import com.fpt.swp391.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	User findUserByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	User findUserByPhone(String phone);

}
