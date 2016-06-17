package com.hengtiansoft.ecommerce.library;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import com.hengtiansoft.ecommerce.library.base.util.CrashHandler;
import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library
 * Description：
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class BasicApplication extends Application {
    private static BasicApplication sApp;
    /** 内存泄露检测工具 **/
    private static RefWatcher sRefWatcher;

    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        CrashHandler.getInstance().init(this);// 崩溃报告分析
        SharedPreferencesUtil.init(this);// 偏好设置初始化
        sRefWatcher = LeakCanary.install(this);// Leakcanary内存泄露分析
    }

    public static Context getAppContext() {
        return sApp;
    }

    public static Resources getAppResources() {
        return sApp.getResources();
    }

    public static RefWatcher getRefWatcher() {
        return sRefWatcher;
    }

}
