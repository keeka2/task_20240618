package com.example.olc.common.error.dto;

public class NotFoundErrorResponse extends BaseErrorResponse {
    public NotFoundErrorResponse() {
        super(CommonErrorConst.NOT_FOUND_ERROR_MESSAGE);
    }
}
