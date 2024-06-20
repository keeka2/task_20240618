package com.example.olc.employee;

import com.example.olc.employee.model.Employee;

import java.util.List;

public interface EmployeeService {
    void uploadEmployeeData(byte[] uploadEmployeeRequest);
    List<Employee> getEmployees(int page);
    Employee getEmployeeByName(String name);
}
