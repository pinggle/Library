package com.hengtiansoft.ecommerce.library.base.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：数据类型转换工具类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class DataFormatUtil {
    private static final String charsetName = "UTF-8";

    /**
     * 字符转化为数字(正整数)
     *
     * @param nStr
     * @return
     */
    public static int parseInt(String nStr) {
        int n;
        try {
            n = Integer.parseInt(nStr);
        } catch (Exception e) {
            n = -1;
            LogUtil.e("parseInt Exception!", e);
        }
        return n;
    }

    /**
     * 字符转化为数字(正整数)
     *
     * @param nStr
     * @param nDefaultValue 默认值
     * @return
     */
    public static int parseInt(String nStr, int nDefaultValue) {
        int n;
        try {
            n = Integer.parseInt(nStr);
        } catch (Exception e) {
            n = nDefaultValue;
            LogUtil.e("parseInt Exception!", e);
        }
        return n;
    }

    /**
     * 百分比值取整
     *
     * @param value
     * @return
     */
    public static String IgnorePoint(String value) {
        int index = value.indexOf(".");
        return value.substring(0, index) + "%";
    }

    /**
     * Description: Base64解密
     *
     * @param str 密文
     * @return
     */
    public static String decryptBase64(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            byte[] encode = str.getBytes(charsetName);
            // base64 解密 
            return new String(Base64.decode(encode, 0, encode.length, Base64.DEFAULT), charsetName);
        } catch (UnsupportedEncodingException e) {
            LogUtil.e(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Description: Base64加密
     *
     * @param str 明文
     * @return
     */
    public static String encryptBase64(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        byte[] decode = str.getBytes();
        return Base64.encodeToString(decode, Base64.DEFAULT);
    }
}
