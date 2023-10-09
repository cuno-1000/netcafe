package com.demo.test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToTimeDifference {
    public static void main(String[] args) {
        String str1 = "2023-06-04 10:00:00";
        String str2 = "2023-06-04 12:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse(str1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(str2, formatter);
        Duration duration = Duration.between(dateTime1, dateTime2);
        System.out.println("時間差為：" + duration.toMinutes() + " 分鐘");
    }
}
