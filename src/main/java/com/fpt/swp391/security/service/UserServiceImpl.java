package com.fpt.swp391.security.service;

import com.fpt.swp391.dto.UserDto;
import com.fpt.swp391.repository.UserRepository;
import com.fpt.swp391.model.User;
import com.fpt.swp391.model.UserRole;
import com.fpt.swp391.security.dto.AuthenticatedUserDto;
import com.fpt.swp391.security.dto.RegistrationRequest;
import com.fpt.swp391.security.dto.RegistrationResponse;
import com.fpt.swp391.security.mapper.UserMapper;
import com.fpt.swp391.service.UserValidationService;
import com.fpt.swp391.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private static final String REGISTRATION_SUCCESSFUL = "registration_successful";

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserValidationService userValidationService;

	private final GeneralMessageAccessor generalMessageAccessor;

	@Override
	public User findByUsername(String username) {

		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> listAllUser() {
		return userRepository.findAll();
	}

	@Override
	public boolean deleteUserByUserName(String username) {
		User user = userRepository.findByUsername(username);
		if(user!= null) {
			userRepository.delete(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User updateUserByUserName(String username, UserDto userDto) {
		User u = userRepository.findByUsername(username);
		if(u != null){
			u.setName(userDto.getName());
			u.setDateOfBirth(userDto.getDateOfBirth());
			u.setPhone(userDto.getPhone());
			u.setAddress(userDto.getAddress());

			if(userDto.getPassword() != null){
				u.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
			}

			if(userDto.getUserRole().equals(UserRole.ROLE_ADMIN.name())) {
				u.setUserRole(UserRole.ROLE_ADMIN);
			}
			if(userDto.getUserRole().equals(UserRole.ROLE_BLOG.name())) {
				u.setUserRole(UserRole.ROLE_BLOG);
			}
			if(userDto.getUserRole().equals(UserRole.ROLE_PRODUCT.name())) {
				u.setUserRole(UserRole.ROLE_PRODUCT);
			}
			if(userDto.getUserRole().equals(UserRole.ROLE_USER.name())) {
				u.setUserRole(UserRole.ROLE_USER);
			}
			userRepository.save(u);
			return u;
		}
		return null;
	}

	@Override
	public RegistrationResponse registration(RegistrationRequest registrationRequest) {

		userValidationService.validateUser(registrationRequest);

		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUserRole(UserRole.ROLE_USER);

		userRepository.save(user);

		final String username = registrationRequest.getUsername();
		final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username);

		log.info("{} registered successfully!", username);

		return new RegistrationResponse(registrationSuccessMessage);
	}

	@Override
	public RegistrationResponse createUserByAdmin(RegistrationRequest registrationRequest, UserRole userRole) {

		userValidationService.validateUser(registrationRequest);

		final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setUserRole(userRole);

		userRepository.save(user);

		final String username = registrationRequest.getUsername();
		final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username);

		log.info("{} registered successfully!", username);

		return new RegistrationResponse(registrationSuccessMessage);
	}

	@Override
	public AuthenticatedUserDto findAuthenticatedUserByUsername(String username) {

		final User user = findByUsername(username);

		return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
	}
}
