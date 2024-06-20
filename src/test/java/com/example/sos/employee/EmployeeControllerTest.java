package com.example.sos.employee;


import com.example.sos.common.util.FileUtil;
import com.example.sos.employee.error.AlreadyExistEmployeeError;
import com.example.sos.employee.error.EmployeeErrorConst;
import com.example.sos.employee.error.InvalidUploadFormatError;
import com.example.sos.employee.error.NotFoundEmployeeError;
import com.example.sos.employee.model.Employee;
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
        given(this.employeeService.getEmployees()).willReturn(
                List.of(
                        Employee.builder()
                                .id(1L)
                                .name("jsk")
                                .email("dwkmd@demk.com")
                                .tel("01012345678")
                                .joined(LocalDateTime.of(2021, 1, 1, 0, 0))
                                .build()
                ));
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
    void postEmployees() throws Exception {
        final String csvUploadString =
                FileUtil.getResourceAsString("sample/csv_upload_test.csv");
        byte[] byteArray = csvUploadString.getBytes();

        willDoNothing().given(employeeService).uploadEmployeeData(byteArray);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .content(byteArray))
                .andExpect(status().isCreated());
    }

    @Test
    void postEmployeesWithInvalidFormat() throws Exception {
        final String invalidFormat = "invalidFormat";
        byte[] byteArray = invalidFormat.getBytes();
        willThrow(new InvalidUploadFormatError()).given(employeeService).uploadEmployeeData(byteArray);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .content(byteArray))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(EmployeeErrorConst.INVALID_UPLOAD_FORMAT_ERROR_MESSAGE));
    }

    @Test
    void postEmployeesWithAlreadyExistingEmployee() throws Exception {
        final String invalidFormat = "alreadyExistingEmployee";
        byte[] byteArray = invalidFormat.getBytes();
        willThrow(new AlreadyExistEmployeeError()).given(employeeService).uploadEmployeeData(byteArray);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                        .content(byteArray))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorMessage").value(EmployeeErrorConst.ALREADY_EXIST_EMPLOYEE_ERROR_MESSAGE));
    }

    @Test
    void getEmployeeByName() throws Exception {
        given(this.employeeService.getEmployeeByName("jsk")).willReturn(
                Employee.builder()
                        .id(1L)
                        .name("jsk")
                        .email("dwkmd@demk.com")
                        .tel("01012345678")
                        .joined(LocalDateTime.of(2021, 1, 1, 0, 0))
                        .build());
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
    void getEmployeeByNameThrowNotFoundEmployee() throws Exception {
        given(this.employeeService.getEmployeeByName("jsk")).willThrow(new NotFoundEmployeeError());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/jsk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(EmployeeErrorConst.NOT_FOUND_EMPLOYEE_ERROR_MESSAGE));
    }
}
