package com.example.sos.employee;

import com.example.sos.employee.error.InvalidUploadFormatError;
import com.example.sos.employee.error.NotFoundEmployeeError;
import com.example.sos.employee.model.Employee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    @Transactional
    public void uploadEmployeeData(final byte[] uploadEmployeeRequest) {
        try {
            final String firstLine = this.getFirstLine(uploadEmployeeRequest);
            if (this.isJsonFormat(firstLine)) {
                //JSON
                final List<Employee> employees = this.parseJsonEmployees(uploadEmployeeRequest);
                this.employeeRepository.saveAll(employees);
            } else if (this.isCSVFormat(firstLine)) {
                // CSV
                final List<Employee> employees = this.parseCsvEmployees(uploadEmployeeRequest);
                this.employeeRepository.saveAll(employees);
            } else {
                throw new IllegalArgumentException("Unsupported format");
            }
        } catch (IOException e) {
            throw new InvalidUploadFormatError();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeByName(final String name) {
        return this.employeeRepository.findByName(name)
                .orElseThrow(NotFoundEmployeeError::new);
    }

    private List<Employee> parseCsvEmployees(final byte[] uploadEmployeeRequest) throws IOException{
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(uploadEmployeeRequest);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8))) {
            String line;
            List<Employee> employees = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                employees.add(Employee.fromCsv(line));
            }
            return employees;
        }
    }

    private List<Employee> parseJsonEmployees(final byte[] uploadEmployeeRequest) throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(uploadEmployeeRequest);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8))) {
            String line;
            final List<Employee> employees = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[") || line.startsWith("]")) {
                    continue;
                }
                employees.add(Employee.fromJson(line.strip().replaceAll(",$", "")));
            }
            return employees;
        }
    }

    private String getFirstLine(final byte[] uploadEmployeeRequest) throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(uploadEmployeeRequest);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8))) {
            return reader.readLine();
        }
    }

    private boolean isJsonFormat(@NonNull final String content) {
        return content.startsWith("[");
    }

    private boolean isCSVFormat(@NonNull final String line) {
        return line.contains(",");
    }
}
