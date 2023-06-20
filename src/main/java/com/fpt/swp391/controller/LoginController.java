package com.fpt.swp391.controller;

import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.security.dto.LoginPhoneRequest;
import com.fpt.swp391.security.dto.LoginRequest;
import com.fpt.swp391.security.dto.LoginResponse;
import com.fpt.swp391.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

	private final JwtTokenService jwtTokenService;

	@PostMapping
	public ResponseEntity<LoginResponse> loginRequest(@Valid @RequestBody LoginRequest loginRequest) {

		final LoginResponse loginResponse = jwtTokenService.getLoginResponse(loginRequest);

		return ResponseEntity.ok(loginResponse);
	}

	@PostMapping("/phone")
	public ResponseEntity<?> loginRequest(@RequestBody LoginPhoneRequest loginPhoneRequest) {

		final LoginResponse loginResponse = jwtTokenService.getLoginPhoneResponse(loginPhoneRequest);

		if(loginResponse != null) {
			return ResponseEntity.ok(loginResponse);
		}
		final ApiExceptionResponse response = new ApiExceptionResponse("ERROR", HttpStatus.BAD_REQUEST, LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}
