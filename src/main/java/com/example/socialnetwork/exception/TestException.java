package com.example.socialnetwork.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TestException extends RuntimeException{

    private final ErrorCode errorCode;

}
