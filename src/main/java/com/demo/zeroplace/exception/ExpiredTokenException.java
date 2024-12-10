package com.demo.zeroplace.exception;

import lombok.Getter;

@Getter
public class ExpiredTokenException extends ZerospaceException{
    private static final String MESSAGE ="만료된 토큰입니다";

    public ExpiredTokenException() { super(MESSAGE);}
    @Override
    public int getStatusCode(){
        return 400;
    }
}
