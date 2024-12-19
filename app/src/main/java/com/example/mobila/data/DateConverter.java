package com.example.mobila.data;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @TypeConverter
    public static String fromDate(Date date) {
        if (date == null) {
            return null;
        }
        return formatter.format(date);
    }

    @TypeConverter
    public static Date toDate(String value) {
        try {
            return formatter.parse(value);
        } catch(ParseException ex) {
            return null;
        }
    }
}
