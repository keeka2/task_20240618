package com.example.sos.employee;

import com.example.sos.common.util.IoUtil;
import com.example.sos.employee.error.AlreadyExistEmployeeError;
import com.example.sos.employee.error.InvalidUploadFormatError;
import com.example.sos.employee.error.NotFoundEmployeeError;
import com.example.sos.employee.model.Employee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
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
            final List<Employee> employees = this.parseEmployees(uploadEmployeeRequest);
            this.employeeRepository.saveAll(employees);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistEmployeeError();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeByName(final String name) {
        return this.employeeRepository.findByName(name)
                .orElseThrow(NotFoundEmployeeError::new);
    }

    private List<Employee> parseEmployees(final byte[] uploadEmployeeRequest) {
        try (final BufferedReader reader = IoUtil.createBufferedReader(uploadEmployeeRequest)) {
            final String firstLine = reader.readLine();
            if (this.isJsonFormat(firstLine)) {
                return parseJsonEmployees(reader);
            } else if (this.isCSVFormat(firstLine)) {
                return parseCsvEmployees(reader, firstLine);
            }
            throw new InvalidUploadFormatError();
        } catch (IOException e) {
            throw new InvalidUploadFormatError();
        }
    }

    private List<Employee> parseCsvEmployees(final BufferedReader reader, final String firstLine) throws IOException{
        String line;
        final List<Employee> employees = new ArrayList<>();
        employees.add(Employee.fromCsv(firstLine));
        while ((line = reader.readLine()) != null) {
            employees.add(Employee.fromCsv(line));
        }
        return employees;
    }

    private List<Employee> parseJsonEmployees(final BufferedReader reader) throws IOException {
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

    private boolean isJsonFormat(@NonNull final String content) {
        return content.startsWith("[");
    }

    private boolean isCSVFormat(@NonNull final String line) {
        return line.contains(",");
    }
}
