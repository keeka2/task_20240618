package com.example.olc.employee.error.dto;

import com.example.olc.common.error.dto.BaseErrorResponse;
import com.example.olc.employee.error.EmployeeErrorConst;

public class NotFoundEmployeeErrorResponse extends BaseErrorResponse {
    public NotFoundEmployeeErrorResponse() {
        super(EmployeeErrorConst.NOT_FOUND_EMPLOYEE_ERROR_MESSAGE);
    }
}
