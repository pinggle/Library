package com.hengtiansoft.ecommerce.library.view.widget;

import android.content.Context;
import android.view.Gravity;

import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.util.AdaptationUtil;

/**
 * ProjectName：fx_android
 * PackageName:  com.hengtiansoft.ecommerce.library.view.widget
 * Description：封装BadgeView
 *
 * @author liminghuang
 * @time 5/12/2016 17:01
 * Modifier：liminghuang
 * ModifyTime：5/12/2016 17:01
 * Comment：
 */
public class MyBadgeView extends BadgeView {
    public MyBadgeView(Context context, int count) {
        super(context);
        setBackground(5, AdaptationUtil.getColor(context, R.color.colorAccent));
        setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        setTextSize(8);
        setTextColor(AdaptationUtil.getColor(context, R.color.colorWhite));
        setBadgeCount(count);
    }

    public MyBadgeView(Context context) {
        super(context);
        setBackground(5, AdaptationUtil.getColor(context, R.color.colorAccent));
        setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        setTextSize(8);
        setTextColor(AdaptationUtil.getColor(context, R.color.colorWhite));
    }
}
