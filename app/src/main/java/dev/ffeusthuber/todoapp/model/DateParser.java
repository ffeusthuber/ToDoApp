package dev.ffeusthuber.todoapp.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateParser {
    private final DateFormat format = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, Locale.GERMAN);

    public Date parseStringtoDate(String dateString) {
        Date date;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
