package com.example.sos.employee;


import com.example.sos.common.util.FileUtil;
import com.example.sos.employee.model.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
                                .tel("010-1234-5678")
                                .joined(LocalDateTime.of(2021, 1, 1, 0, 0))
                                .build()
                ));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("jsk"))
                .andExpect(jsonPath("$[0].email").value("dwkmd@demk.com"))
                .andExpect(jsonPath("$[0].tel").value("010-1234-5678"))
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
    void getEmployeeByName() throws Exception {
        given(this.employeeService.getEmployeeByName("jsk")).willReturn(
                Employee.builder()
                        .id(1L)
                        .name("jsk")
                        .email("dwkmd@demk.com")
                        .tel("010-1234-5678")
                        .joined(LocalDateTime.of(2021, 1, 1, 0, 0))
                        .build());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/jsk")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("jsk"))
                .andExpect(jsonPath("$.email").value("dwkmd@demk.com"));
    }
}
