package com.pas.exception;

import java.util.InputMismatchException;

public class PasswordMismatchExcpetion extends InputMismatchException {
    public PasswordMismatchExcpetion(String message) {
        super(message);
    }
}
