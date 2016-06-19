package com.hengtiansoft.ecommerce.library;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
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

    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
                Log.d("Tom & Jerry",((AVIMTextMessage)message).getText());
            }
        }

        public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client){

        }
    }

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
        AVOSCloud.initialize(this, "vcPB2arj8lyxd0xqSVrddgLK-gzGzoHsz", "VDNyTbqWwavjsf320zXw2WTI");
        //如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
        //注册默认的消息处理逻辑
        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
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
