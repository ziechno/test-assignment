package utils;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Utilities {

    public static LocalDate millisecondToDate(long ms) {
        return Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static ArrayList<String> parseSymbols(String s) {
        String uppercase = s.toUpperCase();
        return new ArrayList<>(Arrays.asList(uppercase.split("\\s*,\\s*")));
    }

    public static Long dateToEpoch(String s) {
        LocalDate date = LocalDate.parse(s);
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant.getEpochSecond();
    }

    public static LocalDateTime epochToTimestamp(String d) {
        Long timeInSeconds = Long.parseLong(d);
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timeInSeconds), ZoneId.systemDefault());
    }


    public static long dateTimeToEpoch(LocalDateTime dateTime) {
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.getEpochSecond();
    }
}
