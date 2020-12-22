package com.hbhb.core.utils;

import java.text.DecimalFormat;

/**
 * @author xiaokang
 */
public class NumberUtil {

    private static final String FORMAT_PATTERN = "###,##0.00";

    public static String format(double number) {
        DecimalFormat df = new DecimalFormat(FORMAT_PATTERN);
        return df.format(number);
    }

}
