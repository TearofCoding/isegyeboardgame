package com.accio.isegye.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    private String message;

    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
