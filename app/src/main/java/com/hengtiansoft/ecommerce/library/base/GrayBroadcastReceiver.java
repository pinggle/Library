package com.hengtiansoft.ecommerce.library.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base
 * Description：启动灰色保活服务的广播接收器
 *
 * @author liminghuang
 * @time 6/24/2016 12:33
 * Modifier：liminghuang
 * ModifyTime：6/24/2016 12:33
 * Comment：
 */
public class GrayBroadcastReceiver extends BroadcastReceiver {
    public GrayBroadcastReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, GrayService.class));
    }
}
