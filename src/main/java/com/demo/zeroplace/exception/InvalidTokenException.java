package com.demo.zeroplace.exception;

public class InvalidTokenException extends ZerospaceException {
    private static final String MESSAGE = "유효하지 않은 토큰입니다.";

    public InvalidTokenException(String message) {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
