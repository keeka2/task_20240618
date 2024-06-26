package com.example.olc.employee.model;

import com.example.olc.common.util.DateTimeUtil;
import com.example.olc.employee.dto.EmployeeJsonRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(
        name = "employee",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
        }
)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull private String name;
    @NonNull private String email;
    @NonNull private String tel;
    @NonNull private LocalDateTime joined;

    public static Employee fromCsv(final String line) {
        final String[] values = line.split(",");
        return Employee.builder()
                .name(values[0])
                .email(values[1])
                .tel(values[2])
                .joined(DateTimeUtil.parsePointDateTime(values[3]))
                .build();
    }

    public static Employee fromJson(final String line) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final EmployeeJsonRequest employeeJsonRequest = objectMapper.readValue(line, EmployeeJsonRequest.class);
        return Employee.builder()
                .name(employeeJsonRequest.getName())
                .email(employeeJsonRequest.getEmail())
                .tel(employeeJsonRequest.getTel().replaceAll("-", ""))
                .joined(DateTimeUtil.parseSlashDateTime(employeeJsonRequest.getJoined()))
                .build();
    }
}
