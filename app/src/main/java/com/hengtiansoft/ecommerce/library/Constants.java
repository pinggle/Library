package com.hengtiansoft.ecommerce.library;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library
 * Description：常量类
 *
 * @author liminghuang
 * @time 6/14/2016 13:57
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 13:57
 * Comment：
 */
public class Constants {
    //base
    public static final int PAGE_COUNT = 10;

    // intent
    public static final String HEAD_DATA = "data";
    public static final String COUNT = "count";
    public static final String VH_CLASS = "vh";// ViewHolder的class类
    public static final String BUNDLE_KEY_TYPE = "type";

    // RxBusEventName
    public static final String EVENT_LOGIN = "login";
    public static final String EVENT_DEL_ITEM = "delete_item";
    public static final String EVENT_UPDATE_ITEM = "update_item";
    public static final String EVENT_HEADDATA = "get_headdata";
    public static final String EVENT_COUNT = "get_count";

    // TagName
    public static final String TAG_EDITABLE = "editable";
    public static final String TAG_HEADDATA = "with_headdata";

    public static final class HttpConstant {
        public static final String HTTP_PROTOCOL = "http://";
    }

}

