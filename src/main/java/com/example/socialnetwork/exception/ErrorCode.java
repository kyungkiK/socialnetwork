package com.example.socialnetwork.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // JWT
    TOKEN_EXPIRED(1000, "토큰이 만료되었습니다"),

    //member
    NOT_FOUND_MEMBER(2100, "존재하지 않는 회원입니다");

    private final int code;
    private final String messages;

    private ErrorCode(int code, String messages) {
        this.code = code;
        this.messages=messages;
    }
}