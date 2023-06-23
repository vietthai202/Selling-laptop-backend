package com.fpt.swp391.controller;

import com.fpt.swp391.dto.ForgotRequest;
import com.fpt.swp391.dto.PasswordRequest;
import com.fpt.swp391.dto.UserDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.User;
import com.fpt.swp391.security.dto.*;
import com.fpt.swp391.security.jwt.JwtTokenManager;
import com.fpt.swp391.security.jwt.JwtTokenService;
import com.fpt.swp391.security.service.UserService;
import com.fpt.swp391.utils.ApiSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final JwtTokenManager jwtTokenManager;

    public UserController(UserService userService, JwtTokenService jwtTokenService, JwtTokenManager jwtTokenManager) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getAllUser() {

        final List<User> listUser = userService.listAllUser();

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : listUser) {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setUsername(user.getUsername());
            dto.setUserRole(user.getUserRole().name());
            dto.setAddress(user.getAddress());

            userDtos.add(dto);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {

            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setDateOfBirth(user.getDateOfBirth());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setUsername(user.getUsername());
            dto.setUserRole(user.getUserRole().name());
            dto.setAddress(user.getAddress());

            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } else {
            final ApiExceptionResponse response = new ApiExceptionResponse("User not found!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<ApiExceptionResponse> deleteUserByUserName(@PathVariable String username) {
        User user = userService.findByUsername(username);
        ApiExceptionResponse response;
        if (user != null) {
            if (!user.getUserRole().equals("ADMIN")) {
                boolean isDelete = userService.deleteUserByUserName(username);
                if (isDelete) {
                    response = new ApiExceptionResponse("Delete user successfully!", HttpStatus.OK, LocalDateTime.now());
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }
            } else {
                response = new ApiExceptionResponse("Can't delete admin!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }
        response = new ApiExceptionResponse("User not found!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/edit/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserDto userDto) {
        ApiExceptionResponse response;
        User u = userService.updateUserByUserName(username, userDto);
        if (u != null) {
            response = new ApiExceptionResponse("Update user successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response = new ApiExceptionResponse("User not found!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/profileEdit/{token}")
    public ResponseEntity<?> profileEdit(@PathVariable String token, @RequestBody UserDto userDto) {
        User u = userService.updateUserProfile(token, userDto);
        if(u != null) {
            return  ResponseEntity.status(HttpStatus.OK).body(null);
        }
        ApiExceptionResponse response = new ApiExceptionResponse("User not found!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PutMapping("/passwordEdit/{token}")
    public ResponseEntity<?> profileEdit(@PathVariable String token, @RequestBody PasswordRequest request) {
        String username = jwtTokenManager.getUsernameFromToken(token);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(request.getOldpass());
        LoginResponse loginResponse = jwtTokenService.getLoginResponse(loginRequest);
        if(loginResponse != null) {
            User u = userService.changePassword(token, request);
            if(u != null) {
                return  ResponseEntity.status(HttpStatus.OK).body(null);
            }
        }
        ApiExceptionResponse response = new ApiExceptionResponse("User not found!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<RegistrationResponse> registrationRequest(@Valid @RequestBody CreateUserRequest createUserRequest) {

        RegistrationRequest request = new RegistrationRequest();
        request.setName(createUserRequest.getName());
        request.setEmail(createUserRequest.getEmail());
        request.setPhone(createUserRequest.getPhone());
        request.setUsername(createUserRequest.getUsername());
        request.setAddress(createUserRequest.getAddress());
        request.setPassword(createUserRequest.getPassword());
        request.setDateOfBirth(createUserRequest.getDateOfBirth());

        final RegistrationResponse registrationResponse = userService.createUserByAdmin(request, createUserRequest.getUserRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }

    @PostMapping("/loss-pass")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotRequest forgotRequest) {
        boolean sent = userService.sendPasswordToEmail(forgotRequest.getEmail());
        if (sent) {
            final ApiSuccessResponse response = new ApiSuccessResponse("Successful", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        final ApiExceptionResponse response = new ApiExceptionResponse("Not Successful", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
