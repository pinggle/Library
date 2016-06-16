package com.hengtiansoft.ecommerce.library.base.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hengtiansoft.ecommerce.library.R;

import java.lang.reflect.Field;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：自动注入View工具类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class ViewUtil {

    /**
     * activity自动findview
     */
    public static void autoFind(Activity activity) {
        try {
            Class<?> clazz = activity.getClass();
            Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
            for (Field field : fields) {
                if (field.getGenericType().toString().contains("widget")
                        || field.getGenericType().toString().contains("view")
                        || field.getGenericType().toString().contains("WebView")) {// 找到所有的view和widget,WebView
                    try {
                        String name = field.getName();
                        Field idfield = R.id.class.getField(name);
                        int id = idfield.getInt(new R.id());// 获得view的id
                        field.setAccessible(true);
                        field.set(activity, activity.findViewById(id));// 给我们要找的字段设置值
                    } catch (IllegalAccessException e) {
                        LogUtil.e("非法访问异常", e);
                    } catch (NoSuchFieldException e) {
                        LogUtil.e("找不到属性", e);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            LogUtil.e("非法数据异常", e);
        }
    }

    /**
     * Fragment以及ViewHolder等自动findview
     */

    public static void autoFind(Object obj, View view) {
        try {
            Class<?> clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
            for (Field field : fields) {
                // getGenericType()返回一个 Type 对象,它表示此 Field 对象所表示字段的声明类型
                // 如果Type是一个参数化类型，则必须准确的反映源码中使用的实际类型参数
                if (field.getGenericType().toString().contains("widget")
                        || field.getGenericType().toString().contains("view")
                        || field.getGenericType().toString()
                        .contains("WebView")) {// 找到所有的view和widget
                    try {
                        String name = field.getName();
                        Field idfield = R.id.class.getField(name);
                        int id = idfield.getInt(new R.id());
                        field.setAccessible(true);
                        field.set(obj, view.findViewById(id));// 给我们要找的字段设置值
                    } catch (IllegalAccessException e) {
                        LogUtil.e("非法访问异常", e);
                    } catch (NoSuchFieldException e) {
                        LogUtil.e("找不到属性", e);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            LogUtil.e("非法数据异常", e);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Activity c) {
        try {
            InputMethodManager imm = (InputMethodManager) c
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(c.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            LogUtil.e("空指针异常", e);
        }
    }
}
