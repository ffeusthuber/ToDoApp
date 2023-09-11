package dev.ffeusthuber.todoapp.model;

import static org.junit.Assert.assertThrows;

import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParserTest extends TestCase {
    public void testParseStringtoDate() {
        DateParser dateParser = new DateParser();

        // Test 1: parsing a valid date-string
        String validDateString = "11.09.2023";
        Date expectedDate = createDate(2023, 9, 11); // Erwartetes Datum erstellen
        Date parsedDate = dateParser.parseStringtoDate(validDateString);
        assertEquals(expectedDate, parsedDate);

        // Test 2: parsing an invalid date-string
        String invalidDateString = "Invalid Date";
        assertThrows(RuntimeException.class, () -> {
            dateParser.parseStringtoDate(invalidDateString);
        });
    }

    private Date createDate(int year, int month, int day) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return dateFormat.parse(String.format("%02d.%02d.%04d", day, month, year));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}