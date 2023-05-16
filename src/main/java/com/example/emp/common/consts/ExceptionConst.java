package com.example.emp.common.consts;

public enum ExceptionConst {
    NO_DATA("No data", "000");

    private String message;
    private String code;

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    ExceptionConst(String message, String code) {
        this.message = message;
        this.code = code;
    }

    ExceptionConst(String message) {
        this.message = message;
    }
}
