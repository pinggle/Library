package com.hengtiansoft.ecommerce.library;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.hengtiansoft.ecommerce.library.base.util.CrashHandler;
import com.hengtiansoft.ecommerce.library.base.util.LogUtil;
import com.hengtiansoft.ecommerce.library.base.util.SharedPreferencesUtil;
import com.hengtiansoft.ecommerce.library.base.util.helper.CustomUserProvider;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.leancloud.chatkit.LCChatKit;

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
    // appId、appKey 可以在「LeanCloud  控制台 / 设置 / 应用 Key」获取
    private final String APP_ID = "vcPB2arj8lyxd0xqSVrddgLK-gzGzoHsz";
    private final String APP_KEY = "VDNyTbqWwavjsf320zXw2WTI";

    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            if (message instanceof AVIMTextMessage) {
                LogUtil.d("消息内容：" + ((AVIMTextMessage) message).getText());
            }
        }

        public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {

        }
    }

    @Override
    public void attachBaseContext(Context base) {
        MultiDex.install(base);// 分包处理
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        CrashHandler.getInstance().init(this);// 崩溃报告分析
        SharedPreferencesUtil.init(this);// 偏好设置初始化
        sRefWatcher = LeakCanary.install(this);// Leakcanary内存泄露分析
        AVOSCloud.initialize(this, APP_ID, APP_KEY);
        //如果使用美国节点，请加上这行代码 AVOSCloud.useAVCloudUS();
        //注册默认的消息处理逻辑
        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
        // 关于 CustomUserProvider 可以参看后面的文档
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
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
