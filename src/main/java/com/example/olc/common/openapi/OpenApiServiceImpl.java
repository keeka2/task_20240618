package com.example.olc.common.openapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class OpenApiServiceImpl implements OpenApiService {

    private static final String OPEN_API_YAML_FILE = "openapi.yaml";

    @Override
    @NonNull
    public String generateOpenApiYamlContent() {
        final InputStream inputStream = this.getResourceInputStream();
        final Yaml yaml = new Yaml();
        final Map<String, Object> root = yaml.load(inputStream);

        return yaml.dump(root);
    }

    private InputStream getResourceInputStream() {
        final ClassPathResource classPathResource = new ClassPathResource(OPEN_API_YAML_FILE);
        try {
            return classPathResource.getInputStream();
        } catch (IOException e) {
            log.error("Failed to read [classpath:{}] file", OPEN_API_YAML_FILE);
            throw new RuntimeException();
        }
    }
}
