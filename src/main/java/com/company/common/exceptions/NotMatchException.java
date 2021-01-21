package com.company.common.exceptions;

public class NotMatchException extends Exception {
    public NotMatchException(String s1, String s2) {
        super(String.format("% and % do not match.", s1, s2));
    }
}
