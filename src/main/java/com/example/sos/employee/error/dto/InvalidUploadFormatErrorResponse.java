package com.example.sos.employee.error.dto;

import com.example.sos.common.error.dto.BaseErrorResponse;
import com.example.sos.employee.error.EmployeeErrorConst;

public class InvalidUploadFormatErrorResponse extends BaseErrorResponse {
    public InvalidUploadFormatErrorResponse() {
        super(EmployeeErrorConst.INVALID_UPLOAD_FORMAT_ERROR_MESSAGE);
    }
}
