package com.fpt.swp391.controller;

import com.fpt.swp391.dto.UserDto;
import com.fpt.swp391.exceptions.ApiExceptionResponse;
import com.fpt.swp391.model.User;
import com.fpt.swp391.security.dto.DeleteRequest;
import com.fpt.swp391.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUser() {

        final List<User> listUser = userService.listAllUser();

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : listUser) {
            userDtos.add(new UserDto(user.getId(), user.getName(), user.getPhone(), user.getDateOfBirth(), user.getUsername(), user.getEmail(), user.getUserRole(), user.getAddress()));
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDtos);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if(user != null) {
            UserDto userDto = new UserDto(user.getId(), user.getName(), user.getPhone(), user.getDateOfBirth(), user.getUsername(), user.getEmail(), user.getUserRole(), user.getAddress());
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } else {
            final ApiExceptionResponse response = new ApiExceptionResponse("User not found!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/delete")
    public ResponseEntity<ApiExceptionResponse> deleteUserByUserName(@Valid @RequestBody DeleteRequest deleteRequest) {
        User user = userService.findByUsername(deleteRequest.getUsername());
        ApiExceptionResponse response;
        if (user != null) {
            if (!user.getUserRole().equals("ADMIN")) {
                boolean isDelete = userService.deleteUserByUserName(deleteRequest.getUsername());
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

    @PutMapping("/users/update/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserDto userDto) {
        ApiExceptionResponse response;
        User u = userService.updateUserByUserName(username, userDto);
        if(u != null) {
            response = new ApiExceptionResponse("Update user successfully!", HttpStatus.OK, LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response = new ApiExceptionResponse("User not found!", HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
