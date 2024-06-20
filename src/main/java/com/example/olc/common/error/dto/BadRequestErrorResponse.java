package com.example.olc.common.error.dto;

public class BadRequestErrorResponse extends BaseErrorResponse {
    public BadRequestErrorResponse() {
        super(CommonErrorConst.BAD_REQUEST_ERROR_MESSAGE);
    }
}
