package utils;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Utilities {

    public static Integer milisecondToDate(long ms){
        LocalDate result = Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDate();
        return result.getYear();
    }

    public static ArrayList<String> parseSymbols(String s){
        ArrayList<String> symbols = new ArrayList<>(Arrays.asList(s.split("\\s*,\\s*")));
        return symbols;
    }

    public static String dateToEpoch(String s){
        LocalDate date = LocalDate.parse(s);
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        String epoch = String.valueOf(instant.getEpochSecond());
        return epoch;
    }

    public static LocalDateTime epochToTimestamp(String d){
        Long timeInSeconds = Long.parseLong(d);
        LocalDateTime timestamp = LocalDateTime.ofEpochSecond(timeInSeconds, 0, ZoneOffset.UTC);
        return timestamp;
    }


}
