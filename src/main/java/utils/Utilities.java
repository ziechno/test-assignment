package utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

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

    public static Integer milisecondToDate(long ms){
        LocalDate result = Instant.ofEpochMilli(ms).atZone(ZoneId.systemDefault()).toLocalDate();
        return result.getYear();
    }
}
