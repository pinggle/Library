package com.hengtiansoft.ecommerce.library.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：MD5加密工具类
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class Md5Util {
    public static final String ENCODING = "UTF-8";
    public static final String MD5 = "MD5";

    public static String md5(String str) {
        String s = str;
        if (s == null) {
            return "";
        }
        String value = null;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance(MD5);
            value = new String(Base64.encode/*Base64*/(md5.digest(s.getBytes(ENCODING))));
        } catch (NoSuchAlgorithmException ex) {
            LogUtil.e("MD5 String NoSuchAlgorithmException error", ex);
        } catch (Exception ex) {
            LogUtil.e("MD5 String error", ex);
        }
        return value;
    }

    public static String md5ToHex(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(MD5);
        } catch (Exception e) {
            LogUtil.e(e.toString(), e);
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = ((byte) charArray[i]);
        }
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = md5Bytes[i] & 0xFF;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
