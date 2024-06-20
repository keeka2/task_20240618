package com.example.olc.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IoUtil {
    public static BufferedReader createBufferedReader(final byte[] data) {
        return new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(data),
                        StandardCharsets.UTF_8
                )
        );
    }
}
