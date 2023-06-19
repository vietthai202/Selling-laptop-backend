package com.fpt.swp391.security.service;

import com.fpt.swp391.dto.UserDto;
import com.fpt.swp391.model.User;
import com.fpt.swp391.model.UserRole;
import com.fpt.swp391.security.dto.AuthenticatedUserDto;
import com.fpt.swp391.security.dto.RegistrationRequest;
import com.fpt.swp391.security.dto.RegistrationResponse;

import java.util.List;

public interface UserService {
	User findByUsername(String username);
	List<User> listAllUser();
	boolean deleteUserByUserName(String username);
	User updateUserByUserName(String username, UserDto userDto);
	RegistrationResponse registration(RegistrationRequest registrationRequest);
	RegistrationResponse createUserByAdmin(RegistrationRequest registrationRequest, UserRole userRole);
	AuthenticatedUserDto findAuthenticatedUserByUsername(String username);
	boolean sendPasswordToEmail(String email);
	User findByPhoneNumber(String phoneNumber);
}
