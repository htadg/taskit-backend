package com.htadg.taskit.dto.response;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private long expiresIn;
}
