package com.example.sos.employee.error.dto;

import com.example.sos.common.error.dto.BaseErrorResponse;
import com.example.sos.employee.error.EmployeeErrorConst;

public class NotFoundEmployeeErrorResponse extends BaseErrorResponse {
    public NotFoundEmployeeErrorResponse() {
        super(EmployeeErrorConst.NOT_FOUND_EMPLOYEE_ERROR_MESSAGE);
    }
}
