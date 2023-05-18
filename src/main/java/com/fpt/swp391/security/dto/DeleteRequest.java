package com.fpt.swp391.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class DeleteRequest {
    @NotEmpty(message = "{login_username_not_empty}")
    private String username;

}
