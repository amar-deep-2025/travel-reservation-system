package com.travel.identity.exception;

public class AccountDisabledException extends RuntimeException{

    public AccountDisabledException(String message) {
        super(message);
    }
}
