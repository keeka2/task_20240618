package com.example.olc.employee.error.dto;

import com.example.olc.common.error.dto.BaseErrorResponse;
import com.example.olc.employee.error.EmployeeErrorConst;

public class AlreadyExistEmployeeErrorResponse extends BaseErrorResponse {
    public AlreadyExistEmployeeErrorResponse() {
        super(EmployeeErrorConst.ALREADY_EXIST_EMPLOYEE_ERROR_MESSAGE);
    }
}