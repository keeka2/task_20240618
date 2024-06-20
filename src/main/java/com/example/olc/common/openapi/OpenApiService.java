package com.example.olc.common.openapi;

import org.springframework.lang.NonNull;

public interface OpenApiService {
    @NonNull
    String generateOpenApiYamlContent();
}
