package com.example.sos.common.error.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MalformedUrlErrorResponse extends BaseErrorResponse {
    public MalformedUrlErrorResponse() {
        super(CommonErrorConst.MALFORMED_URL_ERROR_MESSAGE);
    }
}
