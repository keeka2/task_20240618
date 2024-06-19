package com.example.sos.employee.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class EmployeeJsonRequest {
    String name;
    String email;
    String tel;
    String joined;
}
