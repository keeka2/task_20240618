package com.example.sos.common.openapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OpenApiController {

    private final OpenApiService openApiService;

    @GetMapping(path = "/docs/openapi.yaml", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getOpenApiYaml() {
        final String content = this.openApiService.generateOpenApiYamlContent();
        return ResponseEntity.ok(content);
    }
}
