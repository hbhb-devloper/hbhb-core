package com.hbhb.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author xiaokang
 */
public class NumberUtil {

    private static final String FORMAT_PATTERN = "###,##0.00";

    public static String formatMoney(BigDecimal money) {
        DecimalFormat df = new DecimalFormat(FORMAT_PATTERN);
        return df.format(money);
    }
}
