package com.example.sos.common.error.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BaseErrorResponse {
    private final String errorMessage;
}
