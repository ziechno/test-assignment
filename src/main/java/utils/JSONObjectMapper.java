package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONObjectMapper {
    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode getObjectByKey(String jsonData, String key) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(jsonData);
        return rootNode.findPath(key);
    }
}


