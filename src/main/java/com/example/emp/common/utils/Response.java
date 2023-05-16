package com.example.emp.common.utils;


import com.example.emp.common.consts.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response {

    private String type;
    private String code;
    private String message;
    private Object data;

    public Response(String type) {
        this.type = type;
    }

    public Response(String code, String message) {
        this.message = message;
        this.code = code;
    }

    public Response(String type, String code, String message) {
        this.type = type;
        this.code = code;
        this.message = message;
    }

    public Response withData(Object data) {
        this.data = data;
        return this;
    }

    public static Response success(String message) {
        return new Response(Constants.RESPONSE_TYPE.SUCCESS, Constants.RESPONSE_CODE.STATUS_CODE_OK, message);
    }

    public static Response success(String code, String message) {
        return new Response(Constants.RESPONSE_TYPE.SUCCESS, code, message, null);
    }

    public static Response error(String data) {
        return new Response(Constants.RESPONSE_TYPE.ERROR, Constants.RESPONSE_CODE.STATUS_CODE_SERVER_ERROR, data);
    }



}
