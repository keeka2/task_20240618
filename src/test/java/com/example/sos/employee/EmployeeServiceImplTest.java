package com.example.sos.employee;

import com.example.sos.common.util.FileUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {

    @Test
    void getEmployees() {
        final String csvUploadTest =
                FileUtil.getResourceAsString("sample/csv_upload_test.csv");
    }

    @Test
    void uploadEmployeeData() {
    }

    @Test
    void getEmployeeByName() {
    }
}