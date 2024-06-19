package com.example.sos.common.util;

import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter POINT_DATE_TIME_FORMATTER= DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    private static final DateTimeFormatter SLASH_DATE_TIME_FORMATTER= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @NonNull
    public static LocalDateTime parsePointDateTime(String dateString) {
        return LocalDateTime.parse(dateString + " 00:00:00", POINT_DATE_TIME_FORMATTER);
    }

    @NonNull
    public static LocalDateTime parseSlashDateTime(String dateString) {
        return LocalDateTime.parse(dateString + " 00:00:00", SLASH_DATE_TIME_FORMATTER);
    }

}
