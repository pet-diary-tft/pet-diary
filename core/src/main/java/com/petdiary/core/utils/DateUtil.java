package com.petdiary.core.utils;

import java.time.*;
import java.util.Date;

public class DateUtil {
    /**
     * LocalDateTime 객체를 timestamp로 전환하는 함수
     * @return LocalDateTime가 null일 경우 null을 반환
     */
    public static Long convertToTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return instant.toEpochMilli();
    }

    /**
     * LocalDate 객체를 timestamp로 전환하는 함수
     * @return LocalDate가 null일 경우 null을 반환
     */
    public static Long convertToTimestamp(LocalDate localDate) {
        if (localDate == null) return null;
        Instant instant = localDate.atStartOfDay(ZoneOffset.UTC).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * timestamp를 LocalDate 객체로 전환하는 함수
     * @return 전환 실패시 null을 반환
     */
    public static LocalDate convertToLocalDate(Long timestamp) {
        try {
            Instant instant = Instant.ofEpochMilli(timestamp);
            return instant.atOffset(ZoneOffset.UTC).toLocalDate();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * timestamp를 LocalDateTime 객체로 전환하는 함수
     * @return 전환 실패시 null을 반환
     */
    public static LocalDateTime convertToLocalDateTime(Long timestamp) {
        try {
            Instant instant = Instant.ofEpochMilli(timestamp);
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertToString(LocalDate localDate) {
        return localDate != null ? localDate.toString() : "";
    }

    public static String convertToString(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toString() : "";
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate convertToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime plushHour(LocalDateTime localDateTime, int hours) {
        return localDateTime == null ? null : localDateTime.plusHours(hours);
    }
}
