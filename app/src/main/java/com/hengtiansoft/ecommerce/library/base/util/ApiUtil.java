package com.hengtiansoft.ecommerce.library.base.util;

import android.text.TextUtils;

import com.hengtiansoft.ecommerce.library.base.BaseEntity;
import com.hengtiansoft.ecommerce.library.data.Pointer;

import java.util.Map;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：API接口参数转换的工具类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class ApiUtil {

    public static final String TEXT_INCLUDE = "include";

    /**
     * 根据传入的param构建接口的查询语句
     *
     * @param param
     * @return
     */
    public static String getWhere(Map<String, String> param) {
        String where = "";
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (!TextUtils.equals(entry.getKey(), TEXT_INCLUDE)) {
                boolean isJson = entry.getValue().endsWith("}");
                where += "\"" + entry.getKey() + "\":" + (isJson ? "" : "\"") + entry.getValue() + (isJson ? "" :
                        "\"") + ",";
            }
        }
        return "{" + where.substring(0, where.length() - 1) + "}";
    }

    public static <T extends BaseEntity.BaseBean> Pointer getPointer(T obj) {
        return new Pointer(obj.getClass().getSimpleName(), obj.objectId);
    }

    public static String getInclude(Map<String, String> param) {
        String include = "";
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (TextUtils.equals(entry.getKey(), TEXT_INCLUDE)) {
                include = entry.getValue();
                break;
            }
        }
        return include;
    }
}
