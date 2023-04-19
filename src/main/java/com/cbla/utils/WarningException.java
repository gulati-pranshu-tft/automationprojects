package com.cbla.utils;

public class WarningException extends Exception {

    private static final long serialVersionUID = 1L;

    public WarningException(String message) {
        super("WARNING! " + message);
    }

    public WarningException(String message, Throwable cause) {
        super("WARNING! " + message, cause);
    }
}
