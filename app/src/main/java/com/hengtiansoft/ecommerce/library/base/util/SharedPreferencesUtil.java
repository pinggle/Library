package com.hengtiansoft.ecommerce.library.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.hengtiansoft.ecommerce.library.base.BaseActivity;
import com.hengtiansoft.ecommerce.library.data.entity._User;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：偏好设置工具类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class SharedPreferencesUtil {
    static SharedPreferences prefs;
    public static final String PREFS_KEY_IS_NIGHT = "isNight";
    public static final String PREFS_KEY_USER = "user";
    public static final String PREFS_KEY_VERSION_CODE = "versionCode";

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isNight() {
        return prefs.getBoolean(PREFS_KEY_IS_NIGHT, false);
    }

    public static void setNight(Context context, boolean isNight) {
        prefs.edit().putBoolean(PREFS_KEY_IS_NIGHT, isNight).commit();
        if (context instanceof BaseActivity)
            ((BaseActivity) context).reload();
    }

    public static _User getUser() {
        return new Gson().fromJson(prefs.getString(PREFS_KEY_USER, ""), _User.class);
    }

    public static void setUser(_User user) {
        prefs.edit().putString(PREFS_KEY_USER, new Gson().toJson(user)).commit();
    }

    public static String getVersionCode() {
        return prefs.getString(PREFS_KEY_VERSION_CODE, "");
    }

    public static void setVersionCode(String versionCode) {
        prefs.edit().putString(PREFS_KEY_VERSION_CODE, versionCode).commit();
    }

    public static boolean hasKey(final String key) {
        return prefs.contains(key);
    }

    public static void clear() {
        final SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
