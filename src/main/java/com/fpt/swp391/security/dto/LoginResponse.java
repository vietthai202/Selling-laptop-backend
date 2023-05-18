package com.fpt.swp391.security.dto;

import com.fpt.swp391.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
	private String token;
	private UserRole userRole;
	private String username;

}
