package com.application.exceptions;

public class NotEnoughBalanceException
        extends Exception {
    public NotEnoughBalanceException(String s) {
        super(s);
    }
}