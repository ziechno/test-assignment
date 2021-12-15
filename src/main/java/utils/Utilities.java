package utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Utilities {

    public static JSONObject getAssetProfileJSON(String s){
        JSONObject jsonObject = new JSONObject(s);
        JSONObject quoteSummary = jsonObject.getJSONObject("quoteSummary");
        JSONArray resultArray = quoteSummary.getJSONArray("result");
        JSONObject resultObject = resultArray.getJSONObject(0);
        JSONObject assetProfile = resultObject.getJSONObject("assetProfile");
        return assetProfile;
    }

    public static JSONObject getAssetInfoJSON(String s) {
        JSONObject jsonObject = new JSONObject(s);
        JSONObject quoteResponse = jsonObject.getJSONObject("quoteResponse");
        JSONArray resultArray = quoteResponse.getJSONArray("result");
        JSONObject assetInfo = resultArray.getJSONObject(0);
        return assetInfo;
    }

    public static JSONArray getTimestamps(String s){
        JSONObject jsonObject = new JSONObject(s);
        JSONObject chart = jsonObject.getJSONObject("chart");
        JSONArray resultArray = chart.getJSONArray("result");
        JSONObject resultObject = resultArray.getJSONObject(0);
        JSONArray timestamps = resultObject.getJSONArray("timestamp");
        return timestamps;
    }

    public static JSONArray getQuote(String s){
        JSONObject jsonObject = new JSONObject(s);
        JSONObject chart = jsonObject.getJSONObject("chart");
        JSONArray resultArray = chart.getJSONArray("result");
        JSONObject resultObject = resultArray.getJSONObject(0);
        JSONObject indicators = resultObject.getJSONObject("indicators");
        JSONArray quote = indicators.getJSONArray("quote");
        return quote;
    }

    public static Integer milisecondToDate(long ms){
        LocalDate result = Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDate();
        return result.getYear();
    }

    public static ArrayList<String> parseSymbols(String s){
        ArrayList<String> symbols = new ArrayList<String>(Arrays.asList(s.split("\\s*,\\s*")));
        return symbols;
    }

    public static String dateToEpoch(String s){
        LocalDate date = LocalDate.parse(s);
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        String epoch = String.valueOf(instant.getEpochSecond());
        return epoch;
    }

    public static LocalTime epochToLocalTime(String d){
        Long timeInSeconds = Long.parseLong(d);
        LocalTime time = LocalDateTime.ofEpochSecond(timeInSeconds, 0, ZoneOffset.UTC).toLocalTime();
        return time;
    }
}
