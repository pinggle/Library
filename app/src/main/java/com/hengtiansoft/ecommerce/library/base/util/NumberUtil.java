package com.hengtiansoft.ecommerce.library.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：数字处理工具类
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class NumberUtil {
    private static DecimalFormat df = new DecimalFormat("0.00");

    /**
     * 四舍五入保留两位小数
     */
    public static String formatPrice(BigDecimal bigDecimal) {
        return bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
    }

    public static String formatePrice(BigDecimal bigDecimal) {
        DecimalFormat df = new DecimalFormat("0.00"); // 保留几位小数
        String result = df.format(bigDecimal);
        return result;
    }

    public static BigDecimal StrToBigDecimal(String str) {
        return new BigDecimal(str).divide(new BigDecimal(100));
    }

    public static BigDecimal LongToBigDecimal(Long lon) {
        return new BigDecimal(lon).divide(new BigDecimal(100));
    }

    /**
     * 四舍五入保留两位小数
     */
    public static String formatPrice(double b) {
        return formatPrice(BigDecimal.valueOf(b));
    }

    private static NumberFormat numberFormat;

    /**
     * 数字转成2位小数金钱格式
     */
    public static String getPrice(double d) {
        if (numberFormat == null) {
            numberFormat = NumberFormat.getCurrencyInstance();
        }
        return numberFormat.format(d);
    }

    /**
     * 2位小数金钱转成数字格式
     */
    public static String getPrice(String money) {
        if (numberFormat == null) {
            numberFormat = NumberFormat.getCurrencyInstance();
        }
        try {
            return numberFormat.parse(money) + "";
        } catch (ParseException e) {
            LogUtil.e(e);
        }
        return "0.0";
    }

    public static String formatPrice1(double d) {
        return df.format(d);
    }

    public static String formatPrice2(Long d) {
        Double doubleValue = d / 100.0;
        return df.format(doubleValue);
    }

    public static BigDecimal one = new BigDecimal("1");

    public static double round(double v) {
        BigDecimal b = new BigDecimal(Double.toString(v));
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String formatIncomeMoney(String money) {
        String rst = "";
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        if ("null".equals(money) || null == money) {
            rst = "0.00";
        } else {
            rst = format.format(Double.valueOf(money));
        }
        return rst;
    }
}
