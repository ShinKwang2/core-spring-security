package com.lightshoes.corespringsecurity.exception;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
