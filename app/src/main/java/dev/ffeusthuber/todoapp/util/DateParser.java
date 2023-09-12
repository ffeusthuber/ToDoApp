package dev.ffeusthuber.todoapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    private static final DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);

    public static Date parseStringtoDate(String dateString) {
        Date date;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
