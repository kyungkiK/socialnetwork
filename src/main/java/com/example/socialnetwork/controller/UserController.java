package com.example.socialnetwork.controller;

import com.example.socialnetwork.auth.jwt.TokenDto;
import com.example.socialnetwork.dto.LoginDto;
import com.example.socialnetwork.dto.SignInDto;
import com.example.socialnetwork.dto.UserDto;
import com.example.socialnetwork.dto.UserInfoDto;
import com.example.socialnetwork.exception.ErrorCode;
import com.example.socialnetwork.exception.TestException;
import com.example.socialnetwork.repository.UserRepository;
import com.example.socialnetwork.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    //회원가입
    @PostMapping("/sign-in")
    public ResponseEntity join(@RequestBody @Validated SignInDto signInDto) {
        log.info("sing-in 시도");
        userService.save(signInDto);
        log.info("sing-in 2");
        return ResponseEntity.ok(true);
    }

    /**
     * 로그인 성공 시, header(Authorization) : accessToken, Cookie : refreshToken 응답
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginDto loginDto, HttpServletResponse response) {

        TokenDto tokenDto = userService.login(loginDto);

        response.setHeader("Authorization", "Bearer "+tokenDto.getAccessToken());
        response.setHeader("Set-Cookie", setRefreshToken(tokenDto.getRefreshToken()).toString());

        UserDto userDto = userRepository.findByUserId(loginDto.getUserId()).orElseThrow(
                () -> new TestException(ErrorCode.NOT_FOUND_MEMBER)
        );

        return ResponseEntity.ok(new UserInfoDto(userDto.getId(), userDto.getUserId()));
    }

    public ResponseCookie setRefreshToken(String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("RefreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .maxAge(60 * 60 * 24 * 7)  //7일 -> 유효기간
                .sameSite("None")
                .path("/")
                .build();
        return cookie;
    }

    /**
     * 토큰 재발급
     */
    @PostMapping("/refresh")
    public void refresh(@CookieValue(name = "RefreshToken") String refreshToken, HttpServletResponse response) {

        if (refreshToken == null) {

        }
        TokenDto tokenDto = userService.refresh(refreshToken);

        response.setHeader("Authorization", "Bearer "+tokenDto.getAccessToken());
        response.setHeader("Set-Cookie", setRefreshToken(tokenDto.getRefreshToken()).toString());
    }

}