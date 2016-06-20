package com.hengtiansoft.ecommerce.library.base;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.hengtiansoft.ecommerce.library.base.util.LogUtil;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base
 * Description：灰色保活并不代表着你的Service就永生不死了，只能说是提高了进程的优先级实现进程保活。如果你的app进程占用了大量的内存，按照回收进程的策略，同样会干掉你的app
 *
 * @author liminghuang
 * @time 6/20/2016 14:11
 * Modifier：liminghuang
 * ModifyTime：6/20/2016 14:11
 * Comment：
 */
public class GrayService extends Service {
    private final static int GRAY_SERVICE_ID = 1001;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(this.getClass().getSimpleName() + "启动");
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }
}
