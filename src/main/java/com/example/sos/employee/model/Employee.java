package com.example.sos.employee.model;

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
}
