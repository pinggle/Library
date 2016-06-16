package com.hengtiansoft.ecommerce.library.base.util;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：android设备信息管理工具类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class DeviceUtil {
    /**
     * 重启应用
     *
     * @param context
     */
    public static void restartApp(Context context) {
        Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }

    /**
     * 获取手机号
     *
     * @param context
     * @return
     */
    public static String getLineNumber(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getLine1Number();
        } catch (Exception e) {
            LogUtil.e("", e);
        }
        return null;
    }

    /**
     * 设备的IMEI
     */
    public static String getIMEI(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception e) {
            LogUtil.e("", e);
        }
        return null;
    }

    /**
     * 设备的MacAddress
     */
    public static String getMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception e) {
            LogUtil.e("", e);
        }
        return null;
    }

    /**
     * 获取手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取SDK对应数字
     */
    public static int getSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统版本
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
}
