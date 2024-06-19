package com.example.sos.employee.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


class EmployeeTest {

    @Test
    void fromCsv() {
    }

    @Test
    void fromJson() throws JsonProcessingException {
        String json = "{\"name\": \"이무기\", \"email\": \"weapon@clovf.com\", \"tel\": \"010-1111-2424\", \"joined\": \"2020-01-05\"}";
        Employee employee = Employee.fromJson(json);

        assertEquals("이무기", employee.getName());
    }
}