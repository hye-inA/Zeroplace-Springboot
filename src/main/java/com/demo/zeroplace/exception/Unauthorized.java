package com.demo.zeroplace.exception;

public class Unauthorized extends ZerospaceException {

    private static final String MESSAGE = "인증이 필요합니다.";

    public Unauthorized() { super(MESSAGE);}

    @Override
    public int getStatusCode() { return 401; }
}
