package com.demo.zeroplace.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
@Getter
public abstract class ZerospaceException extends RuntimeException {

    public final Map<String, String> validation = new HashMap<>();

    public ZerospaceException(String message) { super(message); }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) { validation.put(fieldName, message);}
}
