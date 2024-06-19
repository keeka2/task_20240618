package com.example.sos.employee.error.dto;

import com.example.sos.common.error.dto.BaseErrorResponse;
import com.example.sos.employee.error.EmployeeErrorConst;

public class AlreadyExistEmployeeErrorResponse extends BaseErrorResponse {
    public AlreadyExistEmployeeErrorResponse() {
        super(EmployeeErrorConst.ALREADY_EXIST_EMPLOYEE_ERROR_MESSAGE);
    }
}