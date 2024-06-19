package com.example.sos.employee;

import com.example.sos.employee.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    void uploadEmployeeData(byte[] uploadEmployeeRequest);
    List<Employee> getEmployees();
    Employee getEmployeeByName(String name);
}
