package com.example.olc.common.error.dto;

public class MethodNotAllowedErrorResponse extends BaseErrorResponse {
    public MethodNotAllowedErrorResponse() {
        super(
                CommonErrorConst.METHOD_NOT_ALLOWED_ERROR_MESSAGE);
    }
}
