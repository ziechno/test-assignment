package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONObjectMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

  public static JsonNode getObjectByKey(String jsonData, String key) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode keyNode = rootNode.findPath(key);
        return keyNode;
    }

   /* public static ArrayNode getArrayByKey(String jsonData, String key) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(jsonData);
        ArrayNode keyArray = rootNode.findPath(key);
        return keyArray;
    }*/

 /*     public static Ticker getTickerInfo(String jsonData) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode keyNode = rootNode.path(key);
        return keyNode;
    }*/

    public static void updateObject(ObjectNode object, String jsonData, String[] keys) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(jsonData);
        for (String key : keys){
            JsonNode keyNode = rootNode.path(key);
            object.put(key, keyNode.asText());
        }
    }

}


