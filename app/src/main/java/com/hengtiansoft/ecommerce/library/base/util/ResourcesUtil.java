package com.hengtiansoft.ecommerce.library.base.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.io.InputStream;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：资源访问工具类
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class ResourcesUtil {

    public static int getDrawabelID(Context context, String name) {
        if (context == null) {
            return 0;
        }

        int id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return id;
    }

    public static int getLayoutID(Context context, String name) {
        if (context == null) {
            return 0;
        }

        int id = context.getResources().getIdentifier(name, "layout", context.getPackageName());
        return id;
    }

    public static int getViewID(Context context, String name) {
        if (context == null) {
            return 0;
        }

        int id = context.getResources().getIdentifier(name, "id", context.getPackageName());
        return id;
    }

    public static int getStringID(Context context, String name) {
        if (context == null) {
            return 0;
        }

        int id = context.getResources().getIdentifier(name, "string", context.getPackageName());
        return id;
    }

    public static String getStringByName(Context context, String name) {
        if (context == null) {
            return "";
        }
        return context.getResources().getString(getStringID(context, name));
    }

    public static int getRawID(Context context, String name) {
        if (context == null) {
            return 0;
        }

        int id = context.getResources().getIdentifier(name, "raw", context.getPackageName());
        return id;
    }

    public static int getStyleID(Context context, String name) {
        if (context == null) {
            return 0;
        }

        int id = context.getResources().getIdentifier(name, "style", context.getPackageName());
        return id;
    }

    /**
     * dp转像素
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 像素转dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据设备信息获取当前分辨率下指定单位对应的像素大小；dip,sp -> px
     *
     * @param context
     * @param unit    单位，ex:TypedValue.COMPLEX_UNIT_DIP
     * @param size    值
     * @return
     */
    public static int getRawSize(Context context, int unit, float size) {
        Resources res;
        if (context == null) {
            res = Resources.getSystem();
        } else {
            res = context.getResources();
        }
        return (int) TypedValue.applyDimension(unit, size, res.getDisplayMetrics());
    }

    /**
     * Description: 需先在values中dimens的进行设置
     *
     * @param context
     * @param index   R.dimen.img_height
     * @return
     */
    public static int getIntFromDimens(Context context, int index) {
        if (context == null) {
            return -1;
        }
        int result = context.getResources().getDimensionPixelSize(index);
        return result;
    }

    /**
     * 获取assets下的配置文件到字节数组
     *
     * @param context
     * @param strAssetFile assets下的文件名
     * @return
     */
    public static byte[] getAssetsBytes(Context context, String strAssetFile) {
        try {
            InputStream is = context.getAssets().open(strAssetFile);
            if (is != null) {
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                return buffer;
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return null;
    }
}
