package com.example.sos.employee.error;


import com.example.sos.employee.error.dto.AlreadyExistEmployeeErrorResponse;
import com.example.sos.employee.error.dto.InvalidUploadFormatErrorResponse;
import com.example.sos.employee.error.dto.NotFoundEmployeeErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EmployeeErrorHandler {
    @ExceptionHandler(NotFoundEmployeeError.class)
    public ResponseEntity<NotFoundEmployeeErrorResponse> handleNotFoundEmployeeError(
            final NotFoundEmployeeError notFoundEmployeeError) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundEmployeeErrorResponse());
    }

    @ExceptionHandler(AlreadyExistEmployeeError.class)
    public ResponseEntity<AlreadyExistEmployeeErrorResponse> handleAlreadyExistingEmployeeIdError(
            final AlreadyExistEmployeeError alreadyExistingEmployeeIdError) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new AlreadyExistEmployeeErrorResponse());
    }

    @ExceptionHandler(InvalidUploadFormatError.class)
    public ResponseEntity<InvalidUploadFormatErrorResponse> handleInvalidUploadFormatError(
            final InvalidUploadFormatError invalidUploadFormatError) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InvalidUploadFormatErrorResponse());
    }
}
