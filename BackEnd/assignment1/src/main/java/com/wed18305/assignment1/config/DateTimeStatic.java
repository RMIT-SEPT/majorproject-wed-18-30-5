package com.wed18305.assignment1.config;

import java.time.format.DateTimeFormatter;

public class DateTimeStatic {
    public static DateTimeFormatter getFormatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // "Day, Month, Year, Hour, Minute."
    }
}