package com.example.olc.employee;


import com.example.olc.common.util.FileUtil;
import com.example.olc.employee.error.AlreadyExistEmployeeError;
import com.example.olc.employee.error.EmployeeErrorConst;
import com.example.olc.employee.error.InvalidUploadFormatError;
import com.example.olc.employee.error.NotFoundEmployeeError;
import com.example.olc.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;

    @Test
    void testGetEmployees() throws Exception {
        // Given: getEmployees 호출 시 1명의 Employee가 있음
        given(this.employeeService.getEmployees(1)).willReturn(
                List.of(
                        Employee.builder()
                                .id(1L)
                                .name("jsk")
                                .email("dwkmd@demk.com")
                                .tel("01012345678")
                                .joined(LocalDateTime.of(2021, 1, 1, 0, 0))
                                .build()
                ));

        // Expect: getEmployees 호출하면 1먕의 회원이 응답으로 옴
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("jsk"))
                .andExpect(jsonPath("$[0].email").value("dwkmd@demk.com"))
                .andExpect(jsonPath("$[0].tel").value("01012345678"))
                .andExpect(jsonPath("$[0].joined").value("2021-01-01T00:00:00"));
    }

    @Test
    void testPostEmployees() throws Exception {
        // Given: csv 파일
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/csv_upload_test.csv");
        byte[] byteArray = csvUploadString.getBytes();
        willDoNothing().given(employeeService).uploadEmployeeData(byteArray);

        // Expect: 201 Created
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .content(byteArray))
                .andExpect(status().isCreated());
    }

    @Test
    void testPostEmployeesWithInvalidFormat() throws Exception {
        // Given: 잘못된 형식의 파일
        final String invalidFormat = "invalidFormat";
        byte[] byteArray = invalidFormat.getBytes();
        willThrow(new InvalidUploadFormatError()).given(employeeService).uploadEmployeeData(byteArray);

        // Expect: 400 Bad Request 응답
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .content(byteArray))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(EmployeeErrorConst.INVALID_UPLOAD_FORMAT_ERROR_MESSAGE));
    }

    @Test
    void testPostEmployeesWithAlreadyExistingEmployee() throws Exception {
        // Given: 이미 존재하는 Employee
        final String invalidFormat = "alreadyExistingEmployee";
        byte[] byteArray = invalidFormat.getBytes();
        willThrow(new AlreadyExistEmployeeError()).given(employeeService).uploadEmployeeData(byteArray);

        // Expect: 409 Conflict 응답
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .content(byteArray))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value(EmployeeErrorConst.ALREADY_EXIST_EMPLOYEE_ERROR_MESSAGE));
    }

    @Test
    void testGetEmployeeByName() throws Exception {
        // Given: 이름이 jsk인 Employee 존재
        given(this.employeeService.getEmployeeByName("jsk")).willReturn(
                Employee.builder()
                        .id(1L)
                        .name("jsk")
                        .email("dwkmd@demk.com")
                        .tel("01012345678")
                        .joined(LocalDateTime.of(2021, 1, 1, 0, 0))
                        .build());

        // Expect: 이름이 jsk인 Employee가 응답
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/jsk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("jsk"))
                .andExpect(jsonPath("$.email").value("dwkmd@demk.com"))
                .andExpect(jsonPath("$.tel").value("01012345678"))
                .andExpect(jsonPath("$.joined").value("2021-01-01T00:00:00"));
    }

    @Test
    void testGetEmployeeByNameThrowNotFoundEmployee() throws Exception {
        // Given: 이름이 jsk인 Employee가 없음
        given(this.employeeService.getEmployeeByName("jsk")).willThrow(new NotFoundEmployeeError());

        // Expect: 404 Not Found 응답
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/jsk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(EmployeeErrorConst.NOT_FOUND_EMPLOYEE_ERROR_MESSAGE));
    }
}
