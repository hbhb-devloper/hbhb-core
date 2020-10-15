package com.hbhb.core.utils;

import org.springframework.util.StringUtils;

/**
 * @author xiaokang
 * @since 2020-10-11
 */
public class CamelUnderlineUtil {

    private static final char UNDERLINE = '_';

    /**
     * 驼峰转下划线
     */
    public static String camel2Underline(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int len = param.length();
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰
     */
    public static String underline2Camel(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int len = param.length();
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
