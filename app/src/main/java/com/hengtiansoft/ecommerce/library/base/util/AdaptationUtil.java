package com.hengtiansoft.ecommerce.library.base.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：android系统版本适配工具类
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class AdaptationUtil {
    public static int getColor(Context context, int rColorId) {
        int colorValue;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            colorValue = context.getResources().getColor(rColorId, context.getTheme());
        } else {
            colorValue = context.getResources().getColor(rColorId);
        }
        return colorValue;
    }

    public static ColorStateList getColorStateList(Context context, int rColorId) {
        ColorStateList colorValue;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            colorValue = context.getResources().getColorStateList(rColorId, context.getTheme());
        } else {
            colorValue = context.getResources().getColorStateList(rColorId);
        }
        return colorValue;
    }

    public static Drawable getBackground(Context context, int rDrawableId) {
        Drawable drawableValue;
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            drawableValue = context.getResources().getDrawable(rDrawableId, context.getTheme());
        } else {
            drawableValue = context.getResources().getDrawable(rDrawableId);
        }
        return drawableValue;
    }

}
