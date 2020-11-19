package com.hbhb.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import org.springframework.util.StringUtils;

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
     * 判断对象是否为合法json字符串
     */
    public static boolean isJson(Object object) {
        if (object == null || !String.class.isAssignableFrom(object.getClass())) {
            return false;
        }
        String string = (String) object;
        if (string.isEmpty()) {
            return false;
        }
        char head = string.charAt(0);
        return head == '[' || head == '{';
    }

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
     * json字符串转对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert2Obj(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return clazz.equals(String.class) ? (T) json : objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    /**
     * 优雅输出json
     */
    public static String prettyJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object object = mapper.readValue(json, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }


}
