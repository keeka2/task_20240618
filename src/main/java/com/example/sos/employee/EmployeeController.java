package com.example.sos.employee;


import com.example.sos.employee.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@CrossOrigin("*")  // 스웨거 테스트 위해 CORS 허용
@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping(path = "/api/employee", consumes = {"application/octet-stream", "text/plain"})
    public ResponseEntity<URI> uploadEmployee(
            final @RequestBody byte[] uploadEmployeeRequest) {
        this.employeeService.uploadEmployeeData(uploadEmployeeRequest);
        return ResponseEntity.created(URI.create("/api/employee")).build();
    }

    @GetMapping(path = "/api/employee")
    public ResponseEntity<List<Employee>> getEmployees() {
        final List<Employee> employees = this.employeeService.getEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping(path = "/api/employee/{name}")
    public ResponseEntity<Employee> getEmployeeByName(final @PathVariable String name) {
        final Employee employee = this.employeeService.getEmployeeByName(name);
        return ResponseEntity.ok(employee);
    }
}
