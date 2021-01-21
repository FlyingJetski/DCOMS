package com.company.common.exceptions;

public class DuplicateException extends Exception {
    public DuplicateException(String s) {
        super(String.format("%s already exists.", s));
    }
}
