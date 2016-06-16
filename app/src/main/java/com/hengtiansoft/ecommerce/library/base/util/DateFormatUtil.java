package com.hengtiansoft.ecommerce.library.base.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：日期类型转换工具类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class DateFormatUtil {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAT_FORMAT = "yyyy-MM-dd";

    /**
     * 默认格式化日期显示e.x 2015-07-23 11:22:35
     *
     * @param date
     * @return
     */
    public static String formatDefaultDate(Date date) {
        if (date == null) {
            return null;
        }
        String strDate;
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            strDate = sdf.format(date);
        } catch (Exception ex) {
            LogUtil.e(ex.getMessage(), ex);
            strDate = null;
        }
        return strDate;
    }

    /**
     * date按自定义格式格式化
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, final String format) {
        if (date == null) {
            return null;
        }
        String strDate;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            strDate = sdf.format(date);
        } catch (Exception ex) {
            LogUtil.e(ex.getMessage(), ex);
            strDate = null;
        }
        return strDate;
    }

    /**
     * 毫秒格式化成默认格式
     *
     * @param millis
     * @return
     */
    public static String formatDefaultDate(long millis) {
        Date date = new Date(millis);
        return formatDefaultDate(date);
    }

    /**
     * 毫秒按自定义格式格式化
     *
     * @param millis
     * @param format
     * @return
     */
    public static String formatDate(long millis, String format) {
        Date date = new Date(millis);
        return formatDate(date, format);
    }

    /**
     * string日期按自定义格式格式化
     *
     * @param strDate
     * @param format
     * @return
     */
    public static Date formatStrToDate(String strDate, String format) {
        if (TextUtils.isEmpty(strDate)) {
            return null;
        }
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(strDate);
        } catch (ParseException ex) {
            LogUtil.e("String日期解析异常", ex);
            date = null;
        }
        return date;
    }

    /**
     * string按自定义格式化成string
     *
     * @param strDate
     * @param format
     * @return
     */
    public static String formatStrToStr(String strDate, String format) {
        if (TextUtils.isEmpty(strDate)) {
            return null;
        }
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            date = sdf.parse(strDate);
        } catch (Exception ex) {
            LogUtil.e("String日期解析异常", ex);
            date = null;
            return strDate;
        }
        return formatDate(date, format);
    }

    /**
     * 将string型日期由旧的格式转成新的格式
     *
     * @param strDate
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String formatDateStr(String strDate, String oldFormat, String newFormat) {
        if (TextUtils.isEmpty(strDate)) {
            return null;
        }
        Date date;
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
        try {
            date = sdf.parse(strDate);
            sdf.applyPattern(newFormat);
        } catch (ParseException ex) {
            LogUtil.e("string日期解析异常", ex);
            date = null;
            return strDate;
        }
        return sdf.format(date);
    }

    /**
     * 增加天数
     *
     * @param d   当前日期
     * @param day 增加天数
     * @return
     */
    public static Date addDate(Date d, long day) {
        long time = d.getTime();
        day = day * 24L * 60L * 60L * 1000L;
        time += day;
        return new Date(time);
    }

    /**
     * 增加天数并返回自定义格式
     *
     * @param date         当前日期
     * @param day          增加天数
     * @param returnFormat 自定义格式
     * @return
     */
    public static String addDays(String date, long day, String returnFormat) {
        Date d = formatStrToDate(date, DEFAULT_DATE_FORMAT);
        if (d == null) {
            return "";
        }
        long time = d.getTime();
        day = day * 24L * 60L * 60L * 1000L;
        time += day;
        return formatDate(time, returnFormat);
    }

    /**
     * 日期转星期
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDateToWeek(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (TextUtils.isEmpty(format)) {
            format = "星期";
        }
        int day = date.getDay();
        String week = format;
        switch (day) {
            case 1:
                week = week + "一";
                break;
            case 2:
                week = week + "二";
                break;
            case 3:
                week = week + "三";
                break;
            case 4:
                week = week + "四";
                break;
            case 5:
                week = week + "五";
                break;
            case 6:
                week = week + "六";
                break;
            case 0:
                week = week + "日";
                break;
        }
        return week;
    }

    /**
     * 日期转星期（中英文）
     *
     * @param date     当前日期
     * @param location 中/英(1)
     * @return
     */
    public static String getWeek(Date date, int location) {
        if (date != null) {
            int day = date.getDay();
            String Sun = "周日";
            String Mon = "周一";
            String Tue = "周二";
            String Wed = "周三";
            String Thu = "周四";
            String Fri = "周五";
            String Sat = "周六";
            if (location == 1) {
                Sun = "Sun";
                Mon = "Mon";
                Tue = "Tue";
                Wed = "Wed";
                Thu = "Thu";
                Fri = "Fri";
                Sat = "Sat";
            }
            switch (day) {
                case 0:
                    return Sun;
                case 1:
                    return Mon;
                case 2:
                    return Tue;
                case 3:
                    return Wed;
                case 4:
                    return Thu;
                case 5:
                    return Fri;
                case 6:
                    return Sat;
            }
            return null;
        }
        return null;
    }

    /**
     * 获取星期，默认英文
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        return getWeek(date, 1);
    }

    public static String formatStrToWeek(String strDate, int location) {
        Date date = formatStrToDate(strDate, YEAR_MONTH_DAT_FORMAT);
        return getWeek(date, location);
    }

    @Deprecated
    public static String formatStrToWeek(String strDate) {
        Date date = formatStrToDate(strDate, YEAR_MONTH_DAT_FORMAT);
        return getWeek(date);
    }

    /**
     * 求时间差
     *
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0L;
        SimpleDateFormat format = new SimpleDateFormat(YEAR_MONTH_DAT_FORMAT);
        try {
            Date beginDate = format.parse(beginDateStr);
            Date endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) /
                    86400000L;
        } catch (ParseException e) {
            LogUtil.e(e.getMessage(), e);
        }
        return day;
    }

    /**
     * 自定义格式按开始结束时间格式化
     *
     * @param startTime
     * @param finishTime
     * @param fomater
     * @return
     */
    public static String formatDate(String startTime, String finishTime, String fomater) {
        return formatDate(startTime, finishTime, fomater, 1);
    }

    /**
     * 自定义格式按开始结束时间格式化
     *
     * @param startTime
     * @param finishTime
     * @param fomater
     * @return
     */
    public static String formatDate(String startTime, String finishTime, String fomater, int location) {
        if ((isEmpty(startTime)) || (isEmpty(finishTime))) {
            return "";
        }
        StringBuffer sb = new StringBuffer("");
        if ((fomater == null) || (fomater.length() == 0)) {
            fomater = DEFAULT_DATE_FORMAT;
        }
        if ((startTime.length() < fomater.length()) &&
                (finishTime.length() < fomater.length())) {
            fomater = YEAR_MONTH_DAT_FORMAT;
        }
        String year = "年";
        String month = "月";
        String day = "日";
        String separ = "-";
        if (location != 1) {
            year = "-";
            month = "-";
            day = " ";
            separ = "~";
        }
        String zero = "00:00";
        String zeroTime = " HH:mm";
        String stime = startTime.substring(startTime.length() - 5,
                startTime.length());
        String etime = startTime.substring(finishTime.length() - 5,
                finishTime.length());
        if ((!isEmpty(stime)) && (!isEmpty(etime)) && (stime.equals(etime)) &&
                (stime.equals(zero))) {
            zeroTime = "";
        }
        Date date1 = formatStrToDate(startTime, fomater);
        Date date2 = formatStrToDate(finishTime, fomater);
        if ((date1 != null) && (date2 != null)) {
            if (date1.getYear() == date2.getYear()) {
                if ((date1.getMonth() == date2.getMonth()) &&
                        (date1.getDay() == date2.getDay())) {
                    sb.append(formatDateStr(startTime, fomater,
                            "MM" + month + "dd" + day + " HH:mm"));
                    sb.append(separ);
                    sb.append(formatDateStr(finishTime,
                            fomater, "HH:mm"));
                } else {
                    String sf = "MM" + month + "dd" + day + zeroTime;
                    sb.append(formatDateStr(startTime, fomater,
                            sf));
                    sb.append(separ);
                    sb.append(formatDateStr(finishTime,
                            fomater, sf));
                }
            } else {
                String sf = "yyyy" + year + "MM" + month + "dd" + day +
                        zeroTime;
                sb.append(formatDateStr(startTime, fomater, sf));
                sb.append(separ);
                sb.append(
                        formatDateStr(finishTime, fomater, sf));
            }
        }
        return new String(sb);
    }

    public static String formatDateTime(String startTime, String finishTime, String formater) {
        if ((isEmpty(startTime)) || (isEmpty(finishTime))) {
            return "";
        }
        StringBuffer sb = new StringBuffer("");
        if ((formater == null) || (formater.length() == 0)) {
            formater = DEFAULT_DATE_FORMAT;
        }
        if ((startTime.length() < formater.length()) &&
                (finishTime.length() < formater.length())) {
            formater = YEAR_MONTH_DAT_FORMAT;
        }
        String zero = "00:00";
        String zeroTime = " HH:mm";
        String stime = startTime.substring(startTime.length() - 5,
                startTime.length());
        String etime = startTime.substring(finishTime.length() - 5,
                finishTime.length());
        if ((!isEmpty(stime)) && (!isEmpty(etime)) && (stime.equals(etime)) &&
                (stime.equals(zero))) {
            zeroTime = "";
        }
        Date date1 = formatStrToDate(startTime, formater);
        Date date2 = formatStrToDate(finishTime, formater);
        if ((date1 != null) && (date2 != null)) {
            if (date1.getYear() == date2.getYear()) {
                if ((date1.getMonth() == date2.getMonth()) &&
                        (date1.getDay() == date2.getDay())) {
                    sb.append(formatDateStr(startTime,
                            formater, "yyyy年MM月dd日 HH:mm"));
                    sb.append("-");
                    sb.append(formatDateStr(finishTime,
                            formater, " HH:mm"));
                } else {
                    sb.append(formatDateStr(startTime,
                            formater, "yyyy年MM月dd日"));
                    sb.append("-");
                    sb.append(formatDateStr(finishTime,
                            formater, " dd日"));
                }
            } else {
                String sf = "yyyy年MM月dd日" + zeroTime;
                sb.append(
                        formatDateStr(startTime, formater, sf));
                sb.append("-");
                sb.append(formatDateStr(finishTime, formater,
                        sf));
            }
        }
        return new String(sb);
    }

    public static boolean confineApply(String startTime, String endTime) {
        if ((isEmpty(startTime)) && (isEmpty(endTime))) {
            return false;
        }
        String applyTimeFormate = DEFAULT_DATE_FORMAT;
        Date applyStartDate = formatStrToDate(startTime,
                applyTimeFormate);
        Date applyEndDate = formatStrToDate(endTime,
                applyTimeFormate);
        String temp = formatDate(System.currentTimeMillis(),
                applyTimeFormate);
        Date currentTime = formatStrToDate(temp,
                applyTimeFormate);
        long startDate = -1L;
        long finishDate = -1L;
        if (applyStartDate != null) {
            startDate = currentTime.getTime() - applyStartDate.getTime();
        } else {
            startDate = 1L;
        }
        if (applyEndDate != null) {
            finishDate = applyEndDate.getTime() - currentTime.getTime();
        } else {
            finishDate = 1L;
        }
        return (startDate < 0L) || (finishDate < 0L);
    }

    static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static String formatTime(String createDate) {
        long d1 = fromDateStringToLong(createDate);
        long now = fromDateStringToLong(formatDefaultDate(new Date()));
        long ss = (now - d1) / 1000L;
        int MM = (int) ss / 60;
        int hh = (int) ss / 3600;
        if (hh < 1) {
            if (MM < 1) {
                return "1分钟前";
            }
            return MM + "分钟前";
        }
        if (hh >= 8) {
            return formatDateStr(createDate,
                    DEFAULT_DATE_FORMAT, "MM-dd HH:mm");
        }
        return hh + "小时前";
    }

    public static long fromDateStringToLong(String dateTime) {
        Date date = null;
        SimpleDateFormat inputFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            date = inputFormat.parse(dateTime);
            return date.getTime();
        } catch (Exception e) {
            LogUtil.e(e.getMessage(), e);
        }
        return 0L;
    }

    public static String formateDateEn(String dateTime, String en_fromate, String srcFormate) {
        Date date;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(srcFormate);
            date = inputFormat.parse(dateTime);
            en_fromate = en_fromate == null ? "MM dd, yyyy" : en_fromate;
            DateFormat df1 = new SimpleDateFormat(en_fromate, Locale.ENGLISH);
            return df1.format(date);
        } catch (ParseException e) {
            LogUtil.e("日期解析异常", e);
        }
        return "";
    }

    public static String formateDateLoc(String dateTime, String format, String srcFormate, int locale) {
        Date date = null;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(srcFormate);
            date = inputFormat.parse(dateTime);
            Locale L = Locale.CHINA;
            if (locale == 1) {
                L = Locale.ENGLISH;
            }
            DateFormat df1 = new SimpleDateFormat(format, L);
            return df1.format(date);
        } catch (ParseException e) {
            LogUtil.e("日期解析异常", e);
        }
        return "";
    }

//    public static final class SingleHolder {
//        public static final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
//    }


    /**
     * Description: 时间转换
     *
     * @param date
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formateTime(Date date) {
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm");
        String yearAndMonth = dateSdf.format(date);// 年月
        String hourAndSecond = timeSdf.format(date);// 时分

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (hour >= 0 && hour < 6) {
            return yearAndMonth + " 凌晨" + hourAndSecond;
        } else if (hour >= 6 && hour < 12) {
            return yearAndMonth + " 上午" + hourAndSecond;
        } else if (hour >= 12 && hour < 18) {
            return yearAndMonth + " 下午" + hourAndSecond;
        } else {
            return yearAndMonth + " 晚上" + hourAndSecond;
        }
    }

    /**
     * Description: 格式化时间(判断是今日，昨日，前天)
     *
     * @param sendTime （格式：2015年10月25日）
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(String sendTime) {
        String rst = "";

        if (sendTime == null || "".equals(sendTime)) {
            return "时间为空";
        }

        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat weekSdf = new SimpleDateFormat("E");
        SimpleDateFormat myFormat = new SimpleDateFormat("yy/MM/dd");

        Date date;
        try {
            date = parseFormat.parse(sendTime);// 当前待判断时间点
            Calendar current = Calendar.getInstance();
            current.setTime(date);

            Calendar today = Calendar.getInstance(); // 今天
            today.set(Calendar.YEAR, today.get(Calendar.YEAR));
            today.set(Calendar.MONTH, today.get(Calendar.MONTH));
            today.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));
            today.set(Calendar.HOUR_OF_DAY, 0);// Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);

            Calendar yesterday = Calendar.getInstance(); // 昨天
            yesterday.set(Calendar.YEAR, yesterday.get(Calendar.YEAR));
            yesterday.set(Calendar.MONTH, yesterday.get(Calendar.MONTH));
            yesterday.set(Calendar.DAY_OF_MONTH, yesterday.get(Calendar.DAY_OF_MONTH) - 1);
            yesterday.set(Calendar.HOUR_OF_DAY, 0);
            yesterday.set(Calendar.MINUTE, 0);
            yesterday.set(Calendar.SECOND, 0);

            Calendar beforeYesterday = Calendar.getInstance(); // 前天
            yesterday.set(Calendar.YEAR, beforeYesterday.get(Calendar.YEAR));
            yesterday.set(Calendar.MONTH, beforeYesterday.get(Calendar.MONTH));
            yesterday.set(Calendar.DAY_OF_MONTH, beforeYesterday.get(Calendar.DAY_OF_MONTH) - 2);
            yesterday.set(Calendar.HOUR_OF_DAY, 0);
            yesterday.set(Calendar.MINUTE, 0);
            yesterday.set(Calendar.SECOND, 0);

            if (current.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    && current.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                    && current.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                rst = "今天";
            } else if (current.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR)
                    && current.get(Calendar.MONTH) == yesterday.get(Calendar.MONTH)
                    && current.get(Calendar.DAY_OF_MONTH) == yesterday.get(Calendar.DAY_OF_MONTH)) {
                rst = "昨天";
            } else if (current.get(Calendar.YEAR) == beforeYesterday.get(Calendar.YEAR)
                    && current.get(Calendar.MONTH) == beforeYesterday.get(Calendar.MONTH)
                    && current.get(Calendar.DAY_OF_MONTH) == beforeYesterday.get(Calendar.DAY_OF_MONTH)) {
                rst = weekSdf.format(date);
            } else {
                rst = myFormat.format(date);
            }
        } catch (ParseException e) {
            LogUtil.e(e.getMessage(), e);
        }
        return rst;
    }

    /**
     * Description: 时间格式转换
     *
     * @param date
     * @return
     */
    public static String formatDateToCh(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");

        Date rstDate = new Date();
        try {
            rstDate = sdf.parse(date);
        } catch (ParseException e) {
            LogUtil.e(e.getMessage(), e);
        }

        return sdf2.format(rstDate);
    }
}
