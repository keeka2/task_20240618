package com.example.olc.employee;

import com.example.olc.common.util.FileUtil;
import com.example.olc.employee.error.InvalidUploadFormatError;
import com.example.olc.employee.model.Employee;
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
    void testUploadEmployeeDataViaCsv() {
        // Given: csv 파일
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/csv_upload_test.csv");
        byte[] byteArray = csvUploadString.getBytes();

        // When: uploadEmployeeData 호출
        this.employeeService.uploadEmployeeData(byteArray);

        // Then: csv 파일에 있는 3명이 저장되어 있어야 함
        assertEquals(3, this.employeeRepository.count());
    }

    @Test
    void testUploadEmployeeDataViaJson() {
        // Given: json 파일
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/json_upload.json");
        byte[] byteArray = csvUploadString.getBytes();

        // When: uploadEmployeeData 호출
        this.employeeService.uploadEmployeeData(byteArray);

        // Then: json 파일에 있는 3명이 저장되어 있어야 함
        assertEquals(3, this.employeeRepository.count());
    }

    @Test
    void testUploadEmployeeDataViaNotValid() {
        // Given: 잘못된 형식의 파일
        final String badString = "sdsd";
        byte[] byteArray = badString.getBytes();

        // Expect: uploadEmployeeData 호출하면 InvalidUploadFormatError 발생
        assertThrows(InvalidUploadFormatError.class, () -> this.employeeService.uploadEmployeeData(byteArray));
    }

    @Test
    void testGetEmployees() {
        // Given: json 파일러 사원 업로드
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/json_upload.json");
        byte[] byteArray = csvUploadString.getBytes();
        this.employeeService.uploadEmployeeData(byteArray);

        // When: getEmployees 호출
        final List<Employee> employees = this.employeeService.getEmployees(1);

        // Then: 3명의 사원이 조회되어야 함
        assertEquals(3, employees.size());
        assertEquals(employees.get(0).getName(), "이무기");
        assertEquals(employees.get(1).getName(), "판브이");
        assertEquals(employees.get(2).getName(), "차호빵");
    }

    @Test
    void testGetEmployeesWithBigJson() {
        // Given: json 파일 사원 업로드 (20명 이상)
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/big_json_upload_test.json");
        byte[] byteArray = csvUploadString.getBytes();
        this.employeeService.uploadEmployeeData(byteArray);

        // When: getEmployees 호출
        final List<Employee> employees = this.employeeService.getEmployees(1);

        // Then: 20명의 사원만 조회되어야 함
        assertEquals(20, employees.size());
        assertEquals(employees.get(0).getName(), "이무기");
        assertEquals(employees.get(1).getName(), "판브이");
        assertEquals(employees.get(2).getName(), "차호빵1");
    }

    @Test
    void testGetEmployeeByName() {
        // Given: json 파일 사원 업로드 (이무기 포함된 json 파일)
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/json_upload.json");
        byte[] byteArray = csvUploadString.getBytes();
        this.employeeService.uploadEmployeeData(byteArray);
        this.employeeService.getEmployees(1);

        // When: getEmployeeByName 호출
        final Employee employee = this.employeeService.getEmployeeByName("이무기");

        // Then: 이름이 이무기인 사원이 조회되어야 함
        assertEquals("이무기", employee.getName());
        assertEquals("weapon@clovf.com", employee.getEmail());
        assertEquals("01011112424", employee.getTel());
        assertEquals("2020-01-05T00:00", employee.getJoined().toString());
    }
}