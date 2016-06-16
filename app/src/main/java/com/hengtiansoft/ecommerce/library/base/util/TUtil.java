package com.hengtiansoft.ecommerce.library.base.util;

import java.lang.reflect.ParameterizedType;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：泛型类型自动解析工具类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class TUtil {
    public static <T> T getT(Object o, int i) {
        try {
            // getClass().getGenericSuperclass()表示此Class所表示的实体(类、接口、基本类型或void)的直接超类的Type然后将其转换ParameterizedType
            // getActualTypeArguments()返回表示此类型实际类型参数的 Type 对象的数组。
            // 获得超类的泛型参数的实际类型的实例
            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            LogUtil.e("实例化异常", e);
        } catch (IllegalAccessException e) {
            LogUtil.e("非法访问异常", e);
        } catch (ClassCastException e) {
            LogUtil.e("类型转换异常", e);
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            LogUtil.e("类无法加载异常", e);
        }
        return null;
    }
}
