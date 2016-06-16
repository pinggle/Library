package com.hengtiansoft.ecommerce.library.view.listener;

import android.view.View;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.view.listener
 * Description：屏蔽快速连续点击
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public abstract class OnClickEvent implements View.OnClickListener {

    private static final int timeInterval = 500;//毫秒

    public static long lastTime;

    public abstract void singleClick(View v);

    @Override
    public void onClick(View v) {
        if (onDoubClick()) {
            return;
        }
        singleClick(v);
    }

    public boolean onDoubClick() {
        boolean flag = false;
        long time = System.currentTimeMillis() - lastTime;

        if (time < timeInterval) {
            flag = true;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }
}
