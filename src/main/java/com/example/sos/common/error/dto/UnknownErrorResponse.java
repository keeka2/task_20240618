package com.example.sos.common.error.dto;

public class UnknownErrorResponse extends BaseErrorResponse {
    public UnknownErrorResponse() {
        super(CommonErrorConst.UNKNOWN_ERROR_MESSAGE);
    }
}
