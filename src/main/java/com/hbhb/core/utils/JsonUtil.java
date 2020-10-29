package com.hbhb.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xiaokang
 * @since 2020-09-12
 */
public class JsonUtil {

    /**
     * 对象转json字符串
     */
    public static String convert2Str(Object object) {
        if (object == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按key获取value
     */
    public static Object findByKey(String json, String key) {
        if (json == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode destNode = objectMapper.readTree(json).get(key);
            if (destNode != null) {
                return destNode.asText();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * （从list中）按key获取value
     */
    public static List<String> findByKeyFromArray(String json, String key) {
        if (json == null) {
            return null;
        }
        List<String> result = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(json);
            Iterator<JsonNode> elements = root.elements();
            while (elements.hasNext()) {
                JsonNode node = elements.next();
                Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
                while (iterator.hasNext()) {
                    Map.Entry<String, JsonNode> subNode = iterator.next();
                    if (key.equals(subNode.getKey())) {
                        result.add(subNode.getValue().toString());
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
