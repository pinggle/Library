package com.hengtiansoft.ecommerce.library;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;

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

    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        SharedPreferencesUtil.init(this);
    }

    public static Context getAppContext() {
        return sApp;
    }

    public static Resources getAppResources() {
        return sApp.getResources();
    }

}
