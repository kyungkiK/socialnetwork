package com.example.socialnetwork.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//jackson error @RequestBody(기본 생성자 없으면)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDto {

    private String accessToken;

}