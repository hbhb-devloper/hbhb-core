package com.hbhb.core.utils;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author tpson
 */
public class UriUtil {

    public static String decode(String target) {
        if (StringUtils.isEmpty(target)) {
            return target;
        }
        try {
            return URLDecoder.decode(target, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }
}
