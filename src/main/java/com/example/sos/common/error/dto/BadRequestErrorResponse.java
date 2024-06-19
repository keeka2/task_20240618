package com.example.sos.common.error.dto;

public class BadRequestErrorResponse extends BaseErrorResponse {
    public BadRequestErrorResponse() {
        super(CommonErrorConst.BAD_REQUEST_ERROR_MESSAGE);
    }
}
