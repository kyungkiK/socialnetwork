package com.example.socialnetwork.dto;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;


@Getter
public class LoginDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

}
