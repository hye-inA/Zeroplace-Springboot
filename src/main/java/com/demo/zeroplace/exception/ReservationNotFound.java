package com.demo.zeroplace.exception;

public class ReservationNotFound extends ZerospaceException {

    private static final String MESSAGE = "존재하지 않는 예약입니다.";

    public ReservationNotFound() { super(MESSAGE);}

    @Override
    public int getStatusCode() { return 404; }
}
