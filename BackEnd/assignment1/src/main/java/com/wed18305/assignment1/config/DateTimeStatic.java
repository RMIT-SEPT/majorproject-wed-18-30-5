package com.wed18305.assignment1.config;

import java.time.format.DateTimeFormatter;

public class DateTimeStatic {
    public static DateTimeFormatter getFormatter(){
        // "Year, Month, day, Hour, Minute." e.g '2020-09-07T17:00+10:00'
        return DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mmXXXXX");
    }

    public static String getUTCOffset() {
        return "+10:00";
    }
}