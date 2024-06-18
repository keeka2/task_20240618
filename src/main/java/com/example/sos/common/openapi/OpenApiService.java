package com.example.sos.common.openapi;

import org.springframework.lang.NonNull;

public interface OpenApiService {
    @NonNull
    String generateOpenApiYamlContent();
}
