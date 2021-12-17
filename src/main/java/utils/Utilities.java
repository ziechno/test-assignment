package utils;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Utilities {

    public static Integer milisecondToDate(long ms) {
        LocalDate result = Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDate();
        return result.getYear();
    }

    public static ArrayList<String> parseSymbols(String s) {
        String uppercase = s.toUpperCase();
        ArrayList<String> symbols = new ArrayList<>(Arrays.asList(uppercase.split("\\s*,\\s*")));
        return symbols;
    }

    public static Long dateToEpoch(String s) {
        LocalDate date = LocalDate.parse(s);
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Long epoch = instant.getEpochSecond();
        return epoch;
    }

    public static LocalDateTime epochToTimestamp(String d) {
        Long timeInSeconds = Long.parseLong(d);
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(timeInSeconds), ZoneId.systemDefault());
        return timestamp;
    }


    public static long dateTimeToEpoch(LocalDateTime dateTime) {
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        long epoch = instant.getEpochSecond();
        return epoch;
    }
}
