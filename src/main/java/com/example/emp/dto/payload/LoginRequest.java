package com.example.emp.dto.payload;

import lombok.Data;

@Data
public class LoginRequest {
//    @NotBlank
    private String username;

//    @NotBlank
    private String password;
}
