package com.demo.zeroplace.exception;

public class InValidSigninInformation extends ZerospaceException {

    private static final String MESSAGE ="아이디/비밀번호가 올바르지 않습니다";

    public InValidSigninInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode(){
        return 400;
    }
}
