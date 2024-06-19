package com.example.sos.common.error.dto;

public class NotFoundErrorResponse extends BaseErrorResponse {
    public NotFoundErrorResponse() {
        super(CommonErrorConst.NOT_FOUND_ERROR_MESSAGE);
    }
}
