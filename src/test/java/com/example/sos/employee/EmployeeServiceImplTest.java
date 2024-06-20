package com.example.sos.employee;

import com.example.sos.common.util.FileUtil;
import com.example.sos.employee.error.InvalidUploadFormatError;
import com.example.sos.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class EmployeeServiceImplTest{
    @Autowired private EmployeeService employeeService;
    @Autowired private EmployeeRepository employeeRepository;

    @Test
    void uploadEmployeeDataViaCsv() {
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/csv_upload_test.csv");
        byte[] byteArray = csvUploadString.getBytes();

        this.employeeService.uploadEmployeeData(byteArray);

        assertEquals(3, this.employeeRepository.count());
    }

    @Test
    void uploadEmployeeDataViaJson() {
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/json_upload.json");
        byte[] byteArray = csvUploadString.getBytes();

        this.employeeService.uploadEmployeeData(byteArray);

        assertEquals(3, this.employeeRepository.count());
    }

    @Test
    void uploadEmployeeDataViaNotValid() {
        final String badString = "sdsd";
        byte[] byteArray = badString.getBytes();

        assertThrows(InvalidUploadFormatError.class, () -> this.employeeService.uploadEmployeeData(byteArray));
    }

    @Test
    void getEmployees() {
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/json_upload.json");
        byte[] byteArray = csvUploadString.getBytes();
        this.employeeService.uploadEmployeeData(byteArray);

        final List<Employee> employees = this.employeeService.getEmployees();
        assertEquals(3, employees.size());
        assertEquals(employees.get(0).getName(), "이무기");
        assertEquals(employees.get(1).getName(), "판브이");
        assertEquals(employees.get(2).getName(), "차호빵");
    }

    @Test
    void getEmployeeByName() {
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/json_upload.json");
        byte[] byteArray = csvUploadString.getBytes();
        this.employeeService.uploadEmployeeData(byteArray);
        this.employeeService.getEmployees();

        final Employee employee = this.employeeService.getEmployeeByName("이무기");
        assertEquals("이무기", employee.getName());
        assertEquals("weapon@clovf.com", employee.getEmail());
        assertEquals("01011112424", employee.getTel());
        assertEquals("2020-01-05T00:00", employee.getJoined().toString());
    }
}