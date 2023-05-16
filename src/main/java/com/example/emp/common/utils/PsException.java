package com.example.emp.common.utils;

import com.example.emp.common.consts.ExceptionConst;

public class PsException extends RuntimeException{
    private String errorCode;
    private String message;

    public PsException(final Throwable exception) {
        super(exception);
    }

    public PsException(final String message, final Throwable exception) {
        super(message ,exception);
        this.message = message;
    }

    public PsException(final String message, final String errorCode, final Throwable exception) {
        super(message, exception);
        this.message = message;
        this.errorCode = errorCode;
    }

    public PsException(final String message) {
        super(message);
        this.message = message;
    }

    public PsException(final String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

    public PsException(ExceptionConst exceptionConst) {
        super(exceptionConst.getMessage());
        this.message = exceptionConst.getMessage();
        this.errorCode = exceptionConst.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
