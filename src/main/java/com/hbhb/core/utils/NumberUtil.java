package com.hbhb.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author xiaokang
 */
public class NumberUtil {

    private static final String FORMAT_PATTERN = "###,##0.00";

    public static BigDecimal formatMoney(BigDecimal money) {
        DecimalFormat df = new DecimalFormat(FORMAT_PATTERN);
        return new BigDecimal(df.format(money));
    }
}
