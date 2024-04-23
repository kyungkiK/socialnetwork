package com.example.socialnetwork.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInDto {
    @NotBlank
    private String userId;

    @NotBlank
    private String password;
}