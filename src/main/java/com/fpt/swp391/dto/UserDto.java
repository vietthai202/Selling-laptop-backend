package com.fpt.swp391.dto;

import com.fpt.swp391.model.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String phone;
    private Date dateOfBirth;
    private String username;
    private String email;
    private String userRole;
    private String address;
}
