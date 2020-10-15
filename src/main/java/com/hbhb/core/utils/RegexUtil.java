package com.hbhb.core.utils;

import java.util.regex.Pattern;

/**
 * @author xiaokang
 * @since 2020-07-20
 */
public class RegexUtil {

    public static final String YEAR_REGEX = "[0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3}";

    public static boolean isYear(String year) {
        return Pattern.matches(YEAR_REGEX, year);
    }

    public static boolean isNotStartWith(String origin, String target) {
        return origin.matches("^(?!" + target + ").*$");
    }
}
