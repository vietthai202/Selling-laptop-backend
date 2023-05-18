package com.fpt.swp391.service;

import com.fpt.swp391.exceptions.RegistrationException;
import com.fpt.swp391.repository.UserRepository;
import com.fpt.swp391.security.dto.RegistrationRequest;
import com.fpt.swp391.utils.ExceptionMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

	private static final String EMAIL_ALREADY_EXISTS = "email_already_exists";
	private static final String PASSWORD_INVALID = "password_invalid";
	private static final String USERNAME_ALREADY_EXISTS = "username_already_exists";

	private final UserRepository userRepository;

	private final ExceptionMessageAccessor exceptionMessageAccessor;

	public void validateUser(RegistrationRequest registrationRequest) {

		final String email = registrationRequest.getEmail();
		final String username = registrationRequest.getUsername();
		final String password = registrationRequest.getPassword();

		checkEmail(email);
		checkUsername(username);
		checkPassword(password);
	}

	private void checkUsername(String username) {

		final boolean existsByUsername = userRepository.existsByUsername(username);

		if (existsByUsername) {

			log.warn("{} is already being used!", username);

			final String existsUsername = exceptionMessageAccessor.getMessage(null, USERNAME_ALREADY_EXISTS);
			throw new RegistrationException(existsUsername);
		}

	}

	private void checkEmail(String email) {

		final boolean existsByEmail = userRepository.existsByEmail(email);

		if (existsByEmail) {

			log.warn("{} is already being used!", email);

			final String existsEmail = exceptionMessageAccessor.getMessage(null, EMAIL_ALREADY_EXISTS);
			throw new RegistrationException(existsEmail);
		}
	}

	private void checkPassword(String password) {

		boolean passwordIsOk = isStrongPassword(password);
		if (!passwordIsOk) {

			log.warn("{} is invalid format!", password);

			final String invalidPassword = exceptionMessageAccessor.getMessage(null, PASSWORD_INVALID);
			throw new RegistrationException(invalidPassword);
		}
	}

	public static boolean isStrongPassword(String password) {
		String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.*[^\\da-zA-Z]).{6,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}
}
