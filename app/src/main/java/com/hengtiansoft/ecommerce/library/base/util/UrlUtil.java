package com.hengtiansoft.ecommerce.library.base.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：Url附带参数解析工具类
 *
 * @author liminghuang
 * @time 6/15/2016 15:16
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 15:16
 * Comment：
 */
public class UrlUtil {

    /**
     * 将String串写入对应Map中，通常使用在URL路径上获取参数
     *
     * @param strSrc
     * @param GridMap
     * @param VlaueMap
     */
    public static void GetMapValue(String strSrc, Map<String, String[]> GridMap, Map<String, String> VlaueMap) {
        GetMapValue(strSrc, GridMap, VlaueMap, "\r\n", true);
    }

    /**
     * url地址中截取参数
     *
     * @param strSrc
     * @param GridMap
     * @param VlaueMap
     * @param strSplitFlag
     * @param bKeyToLower
     */
    public static void GetMapValue(String strSrc, Map<String, String[]> GridMap, Map<String, String> VlaueMap,
                                   String strSplitFlag, boolean bKeyToLower) {
        int iSubBegin = 0;
        int iBegin = -1;
        iBegin = strSrc.indexOf("<GRID");
        int iGridb = -1;
        String strGrid = "";
        String strGridValue = "";
        int iEnd = -1;
        LinkedList<String> ValueList = new LinkedList<String>();

        if (GridMap != null)
            GridMap.clear();
        if (VlaueMap != null)
            VlaueMap.clear();

        int lCountSize = strSrc.length();

        while (iSubBegin < lCountSize) {
            if (iBegin >= 0)
                iGridb = strSrc.indexOf(">", iBegin);
            if (iGridb >= iSubBegin) {
                if (iGridb > iSubBegin) {
                    String strValue = strSrc.substring(iSubBegin, iBegin);
                    ValueList.add(strValue);
                }
                strGrid = strSrc.substring(iBegin + 1, iGridb);
                iEnd = strSrc.indexOf("</" + strGrid + ">", iGridb);
                if (iEnd >= iSubBegin) {
                    if (GridMap != null) {
                        strGridValue = strSrc.substring(iBegin + strGrid.length() + 2, iEnd);
                        String[] ayGridValue = strGridValue.split(strSplitFlag);
                        GridMap.put(strGrid, ayGridValue);
                    }
                } else {
                    String strValue = strSrc.substring(iSubBegin);
                    ValueList.add(strValue);
                    break;
                }
            } else {
                String strValue = strSrc.substring(iSubBegin);
                ValueList.add(strValue);
                break;
            }
            iSubBegin = iEnd + strGrid.length() + 3;
            iBegin = strSrc.indexOf("<GRID", iSubBegin);
        }
        if (VlaueMap != null) {
            for (int i = 0; i < ValueList.size(); i++) {
                String strValue = ValueList.get(i);
                String[] ayValue = split(strValue, strSplitFlag);
                for (int j = 0; j < ayValue.length; j++) {
                    String strKeyValue = ayValue[j];
                    int iKey = strKeyValue.indexOf("=");
                    if (iKey > 0 && iKey < strKeyValue.length()) {
                        // String finalkey = strKeyValue.substring(0,iKey);
                        // if (bKeyToUpp)
                        // {
                        // finalkey = finalkey.toUpperCase();
                        // }
                        // String finalValue = strKeyValue.substring(iKey+1);
                        // VlaueMap.put(finalkey, finalValue);

                        String finalkey = strKeyValue.substring(0, iKey);
                        String finalValue = strKeyValue.substring(iKey + 1);
                        VlaueMap.put(finalkey.toLowerCase(), finalValue);
                    }
                }
            }
        }
    }

    /**
     * 分割字符串放入ArrayList里
     *
     * @param original 字符串
     * @param regex    分隔符
     * @param AyList   ArrayList
     * @return
     */
    public static void split(String original, String regex, ArrayList<String> AyList) {
        int startIndex = 0;
        if (AyList == null)
            AyList = new ArrayList<String>();
        int index = 0;
        startIndex = original.indexOf(regex);
        while (startIndex < original.length() && startIndex != -1) {
            String temp = original.substring(index, startIndex);
            AyList.add(temp);
            index = startIndex + regex.length();
            startIndex = original.indexOf(regex, startIndex + regex.length());
        }
        if (index < original.length())
            AyList.add(original.substring(index));
        return;
    }

    /**
     * 分割字符串，返回分割后的字符串数组
     *
     * @param original 字符串
     * @param regex    分隔符
     * @return str
     */
    public static String[] split(String original, String regex) {
        if (original == null || original.length() <= 0)
            return null;
        String[] str = null;
        ArrayList<String> AyList = new ArrayList<String>();
        split(original, regex, AyList);
        str = new String[AyList.size()];
        for (int i = 0; i < AyList.size(); i++) {
            str[i] = AyList.get(i);
        }
        return str;
    }
}
