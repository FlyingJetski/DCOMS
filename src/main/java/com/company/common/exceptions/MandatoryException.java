package com.company.common.exceptions;

public class MandatoryException extends Exception {
    public MandatoryException(String s) {
        super(String.format("Mandatory field(s) are missing: %s.", s));
    }
}
