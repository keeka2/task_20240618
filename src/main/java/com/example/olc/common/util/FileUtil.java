package com.example.olc.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtil {
    @NonNull
    public static InputStream getResourceInputStream(@NonNull final String filePath) {
        final ClassPathResource classPathResource = new ClassPathResource(filePath);
        try {
            return classPathResource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @NonNull
    public static String getResourceAsString(@NonNull final String filePath) {
        final InputStream inputStream = getResourceInputStream(filePath);
        try {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
