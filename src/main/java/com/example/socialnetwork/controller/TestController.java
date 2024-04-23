package com.example.socialnetwork.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.security.Key;

@RestController
public class TestController {
    private static final String SECRET_KEY = "64461f01e1af406da538b9c48d801ce59142452199ff112fb5404c8e7e98e3ff"; // 이전에 사용하던 키

    @Value("${jwt.secret}")
    private String jwtSecret;

    private boolean hasCookie = false;

    @GetMapping("/have")
    public ResponseEntity<Object> checkCookie() {
        return ResponseEntity.ok().body("{\"cookie\": " + hasCookie + "}");
    }

    @SneakyThrows
    @PostMapping("/input")
    public ResponseEntity<Object> createCookie(@RequestBody String data, HttpServletResponse response) {
        // JWT 토큰 생성
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes("UTF-8")); // 인코딩 지정
        String jwtToken = Jwts.builder()
                .setSubject(data)
                .signWith(key)
                .compact();

        // JWT 토큰을 쿠키에 저장
        Cookie cookie = new Cookie("JWT_TOKEN", jwtToken);
        cookie.setHttpOnly(true); // XSS 공격 방지를 위해 HttpOnly 설정
        response.addCookie(cookie);

        hasCookie = true;

        return ResponseEntity.ok().body("{\"cookie\": \"" + jwtToken + "\"}");
    }

    @SneakyThrows
    @PostMapping("/change")
    public ResponseEntity<Object> replaceCookie(HttpServletResponse response) {
        // JWT 토큰 생성
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes("UTF-8")); // 인코딩 지정
        String jwtToken = Jwts.builder()
                .setSubject("NewToken")
                .signWith(key)
                .compact();

        // JWT 토큰을 쿠키에 저장 (기존 쿠키를 덮어씀)
        Cookie cookie = new Cookie("JWT_TOKEN", jwtToken);
        cookie.setHttpOnly(true); // XSS 공격 방지를 위해 HttpOnly 설정
        response.addCookie(cookie);

        return ResponseEntity.ok().body("{\"cookie\": \"" + jwtToken + "\"}");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteCookie(HttpServletResponse response) {
        // 쿠키를 삭제하기 위해 만료일을 현재로 설정하여 쿠키 무효화
        Cookie cookie = new Cookie("JWT_TOKEN", "");
        cookie.setMaxAge(0); // 쿠키 유효 시간을 0으로 설정하여 무효화
        response.addCookie(cookie);

        hasCookie = false;

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

