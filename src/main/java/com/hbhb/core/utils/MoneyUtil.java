package com.hbhb.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author xiaokang
 */
public class MoneyUtil {

    private static final String FORMAT_PATTERN = "###,##0.00";

    public static BigDecimal format(BigDecimal money) {
        DecimalFormat df = new DecimalFormat(FORMAT_PATTERN);
        if (money == null) {
            money = BigDecimal.ZERO;
        }
        return new BigDecimal(df.format(money));
    }

}
