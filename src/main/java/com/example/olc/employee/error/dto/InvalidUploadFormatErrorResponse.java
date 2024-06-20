package com.example.olc.employee.error.dto;

import com.example.olc.common.error.dto.BaseErrorResponse;
import com.example.olc.employee.error.EmployeeErrorConst;

public class InvalidUploadFormatErrorResponse extends BaseErrorResponse {
    public InvalidUploadFormatErrorResponse() {
        super(EmployeeErrorConst.INVALID_UPLOAD_FORMAT_ERROR_MESSAGE);
    }
}
