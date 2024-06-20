package com.example.olc.common.error.dto;

public class UnknownErrorResponse extends BaseErrorResponse {
    public UnknownErrorResponse() {
        super(CommonErrorConst.UNKNOWN_ERROR_MESSAGE);
    }
}
