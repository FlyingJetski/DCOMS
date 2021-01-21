package com.company.common.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String s) {
        super(String.format("% is not found.", s));
    }
}
