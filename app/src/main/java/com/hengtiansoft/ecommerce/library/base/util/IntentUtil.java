package com.hengtiansoft.ecommerce.library.base.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.hengtiansoft.ecommerce.library.R;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：Intent包装类
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class IntentUtil {
    public static String PARAMS = "data";
    private static final String URL_HEADER = "http://";
    private static final String MARKET = "market://";
    private static final String FILE = "file://";
    private static final String CONTENT = "content://";
    public static final int INTENT_FLAG = 1074266112;

    /**
     * 显式Intent带数据
     *
     * @param ctx
     * @param c
     * @param params
     * @return
     */
    public static boolean startActivity(Context ctx, Class c, Object... params) {
        if ((ctx == null) || (c == null)) {
            return false;
        }
        try {
            Intent intent = new Intent(ctx, c);
            intent.putExtra(PARAMS, params);
            ctx.startActivity(intent);
            return true;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return false;
    }

    /**
     * 显式Intent无数据
     *
     * @param ctx
     * @param c
     * @return
     */
    public static int startActivity(Context ctx, Class c) {
        try {
            Intent intent = new Intent(ctx, c);
            ctx.startActivity(intent);
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
    }

    /**
     * 获取Intent传递的数据
     *
     * @param bundle
     * @return
     */
    public static Object[] getExtras(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        Object[] obj = (Object[]) bundle.getSerializable(PARAMS);
        return obj;
    }

    /**
     * 获得一个Intent实例
     *
     * @param ctx
     * @param c
     * @return
     */
    public static Intent onCreateIntent(Context ctx, Class c) {
        Intent intent = new Intent(ctx, c);
        return intent;
    }

    /**
     * 打开浏览器
     *
     * @param ctx
     * @param url
     * @return
     */
    public static int onBrowseWeb(Context ctx, String url) {
        if (isEmpty(url)) {
            return -1;
        }
        try {
            if ((!url.startsWith("http://")) && (!url.startsWith("market://")) &&
                    (!url.startsWith("file://")) && (!url.startsWith("content://"))) {
                url = "http://" + url;
            }
            Uri uri = Uri.parse(url);
            Intent it = new Intent("android.intent.action.VIEW", uri);
            ctx.startActivity(it);
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
    }

    /**
     * 打开分享
     *
     * @param ctx
     * @param title
     * @param content
     * @return
     */
    public static int onIntentShare(Context ctx, CharSequence title, CharSequence content) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", title);
            intent.putExtra("android.intent.extra.TEXT", content);
            intent.setFlags(268435456);
            ctx.startActivity(Intent.createChooser(intent, title));
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
    }

    /**
     * 打开地图
     *
     * @param mCtx
     * @param latLng
     * @param address
     */
    public static void openMaps(Context mCtx, String latLng, String address) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri = Uri.parse("geo:" + latLng + "," + address);
        intent.setData(uri);

        mCtx.startActivity(intent);
    }

    /**
     * 打开google地图,需要先安装
     *
     * @param context
     * @param language
     * @param z
     * @param address
     * @param latLng
     * @return
     */
    public static int startMapNavigation(Context context, String language, String z, String address, String latLng) {
        try {
            if (!isEmpty(latLng)) {
                address = latLng + "(" + address + ")";
            }
            if (isEmpty(language)) {
                language = "zh-cn";
            }
            if (isEmpty(z)) {
                z = "18";
            }
            String url = "http://ditu.google.cn/maps?hl=" + language +
                    "&mrt=loc&z=" + z + "&q=" + address;
            String googleMapPackeName = "com.google.android.apps.maps";
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(googleMapPackeName,
                            PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES);// 8192
            if (info != null) {
                Uri uri = Uri.parse(url);
                Intent i = new Intent("android.intent.action.VIEW", uri);
                i.addFlags(0);

                i.setClassName(googleMapPackeName,
                        "com.google.android.maps.MapsActivity");
                context.startActivity(i);
                return 1;
            }
            return onIntentMap(context, language, z, address);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e("找不到指定包名", e);
        }
        return onIntentMap(context, language, z, address);
    }

    public static int onIntentMap(Context ctx, String language, String z, String address) {
        try {
            if (isEmpty(language)) {
                language = "zh-cn";
            }
            if (isEmpty(z)) {
                z = "18";
            }
            String url = "http://ditu.google.cn/maps?hl=" + language +
                    "&mrt=loc&z=" + z + "&q=" + address;
            println(url);
            Uri uri = Uri.parse(url);
            Intent i = new Intent("android.intent.action.VIEW", uri);
            i.addFlags(0);

            ctx.startActivity(i);
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
    }

    /**
     * 打开拨号
     *
     * @param ctx
     * @param telNo
     * @param flag
     * @return
     */
    public static int onIntentCall(Context ctx, String telNo, boolean flag) {
        try {
            if (isEmpty(telNo)) {
                return -1;
            }
            if (!telNo.startsWith("tel:")) {// 构建协议
                telNo = "tel:" + telNo;
            }
            Uri uri = Uri.parse(telNo);
            Intent it;
            if (flag) {
                it = new Intent("android.intent.action.CALL", uri);
            } else {
                it = new Intent("android.intent.action.DIAL", uri);
            }
            ctx.startActivity(it);
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
    }

    /**
     * 打开短信分享
     *
     * @param message
     * @param context
     * @return
     */
    public static int shareSms(String message, Context context) {
        try {
            Intent it = new Intent("android.intent.action.VIEW");
            it.putExtra("sms_body", message);
            it.setType("vnd.android-dir/mms-sms");
            context.startActivity(it);
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
    }

    /**
     * 发信息
     *
     * @param context
     * @param telNo
     * @param smsBody
     * @return
     */
    public static int sendSms(Context context, String telNo, String smsBody) {
        try {
            if (telNo.startsWith("sms:")) {// 协议校验
                if (!telNo.startsWith("smsto:")) {
                    telNo = telNo.replace("sms:", "smsto:");
                }
            } else {
                telNo = "smsto:" + telNo;
            }
            Uri uri = Uri.parse(telNo);
            Intent it = new Intent("android.intent.action.SENDTO", uri);
            it.putExtra("sms_body", smsBody);
            context.startActivity(it);
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
    }

    /**
     * 发送邮件
     *
     * @param ctx
     * @param emailAddress
     * @return
     */
    public static int onIntentEmail(Context ctx, String emailAddress) {
        try {
            if ((isEmpty(emailAddress)) || (!emailAddress.contains("@"))) {
                return 0;
            }
            if (!emailAddress.startsWith("mailto:")) {// 协议校验
                emailAddress = "mailto:" + emailAddress;
            }
            Uri uri = Uri.parse(emailAddress);
            Intent it = new Intent("android.intent.action.SENDTO", uri);
            ctx.startActivity(it);
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
    }


    public static int onSendEmail(Context ctx, String[] emails, String subject, String content) {
        try {
            if ((emails == null) || (emails.length < 1)) {
                return -1;
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.EMAIL", emails);
            intent.putExtra("android.intent.extra.SUBJECT", subject);
            intent.putExtra("android.intent.extra.TEXT", content);
            ctx.startActivity(Intent.createChooser(intent, "Sending..."));
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
    }

    static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    static void println(String str) {
        LogUtil.d(str);
    }

    /******************************** 获取指定类型的Intent *************************************/

    public static Intent getHtmlFileIntent(File file) {
        Uri uri = Uri.parse(file.toString()).buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(file.toString()).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    public static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(268435456);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    public static Intent getPdfFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(268435456);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    public static Intent getTextFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(268435456);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    public static Intent getAudioFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(67108864);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    public static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(67108864);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    public static Intent getChmFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(268435456);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    public static Intent getWordFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(268435456);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    public static Intent getExcelFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(268435456);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    public static Intent getPPTFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(268435456);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    public static Intent getApkFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }

    public static Intent getAllFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "*/*");
        return intent;
    }

    /*************************************** 获取指定Intent结束 ******************************************/

    private static boolean checkEndsWithInStringArray(String checkItsEnd, String[] fileEndings) {
        String[] arrayOfString;
        int j = (arrayOfString = fileEndings).length;
        for (int i = 0; i < j; i++) {
            String aEnd = arrayOfString[i];
            if (checkItsEnd.endsWith(aEnd)) {
                return true;
            }
        }
        return false;
    }

//    public static Intent getIntent(Context context, File currentPath) {
//        Intent intent = null;
//        if ((currentPath != null) && (currentPath.isFile())) {
//            String fileName = currentPath.toString();
//            Resources res = context.getResources();
//            try {
//                if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingImage))) {
//                    intent = getImageFileIntent(currentPath);
//                } else if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingWebText))) {
//                    intent = getHtmlFileIntent(currentPath);
//                } else if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingPackage))) {
//                    intent = getApkFileIntent(currentPath);
//                } else if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingAudio))) {
//                    intent = getAudioFileIntent(currentPath);
//                } else if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingVideo))) {
//                    intent = getVideoFileIntent(currentPath);
//                } else if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingText))) {
//                    intent = getTextFileIntent(currentPath);
//                } else if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingPdf))) {
//                    intent = getPdfFileIntent(currentPath);
//                } else if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingWord))) {
//                    intent = getWordFileIntent(currentPath);
//                } else if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingExcel))) {
//                    intent = getExcelFileIntent(currentPath);
//                } else if (checkEndsWithInStringArray(fileName, res.getStringArray(R.array.fileEndingPPT))) {
//                    intent = getPPTFileIntent(currentPath);
//                } else {
//                    intent = getAllFileIntent(currentPath);
//                }
//            } catch (Exception e) {
//                LogUtil.e(e);
//                intent = null;
//            }
//        }
//        return intent;
//    }
//
//    public static int openFile(Context context, File currentPath) {
//        int ret = -1;
//        Intent intent = getIntent(context, currentPath);
//        if (intent == null) {
//            return 1;
//        }
//        context.startActivity(intent);
//        return ret;
//    }

    /**
     * 打开微信，需要先安装微信
     *
     * @param context
     * @param wxUrl
     * @return
     */
    @Deprecated
    public static int openWXApp(Context context, String wxUrl) {
        try {
            String wxAppPackage = "com.tencent.mm";
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(wxAppPackage,
                            PackageManager.GET_SHARED_LIBRARY_FILES | PackageManager.GET_META_DATA);//8192
            if (info != null) {
                Uri uri = Uri.parse(wxUrl);
                Intent i = new Intent();
                i.setData(uri);
                i.setPackage(wxAppPackage);
                i.setFlags(1074266112);
                i.putExtra("android.intent.extra.SUBJECT", "");
                context.startActivity(i);
                return 1;
            }
            return -1;
        } catch (Exception e) {
            LogUtil.e(e.getMessage(), e);
        }
        return -1;
    }

    /**
     * 打开微信，需要先安装微信
     *
     * @param context
     * @param wxUrl
     * @return
     */
    public static int openWXApp(final Context context, String wxUrl, CharSequence wxNo, String tip) {
        try {
            String wxAppPackage = "com.tencent.mm";
            String versionName = context.getPackageManager().getPackageInfo(wxAppPackage, 0).versionName;
            if (versionName.indexOf('.') != -1) {
                versionName = versionName.split("\\.")[0];
            }
            float fv = Float.parseFloat(versionName);
            if (fv >= 5.0F) {
                ClipboardManager clipManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipManager.setText(wxNo);
                if (TextUtils.isEmpty(tip)) {
                    tip = context.getString(R.string.wx_interactiveTip);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(17301659);
                builder.setTitle(context.getString(R.string.dialog_title_tip));
                builder.setMessage(tip);
                builder.setPositiveButton(context.getString(R.string.dialog_btn_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog = null;
//                                Intent i = new Intent(IntentUtil.this.toString());
//                                i.setFlags(335544320);
//                                i.setClassName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
//                                context.startActivity(i);
                            }
                        });
                builder.setNegativeButton(context.getString(R.string.dialog_btn_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                dialog = null;
                            }
                        });
                builder.show();
            } else {
                Uri uri = Uri.parse(wxUrl);
                Intent i = new Intent();
                i.setData(uri);
                i.setPackage(wxAppPackage);
                i.setFlags(1074266112);
                context.startActivity(i);
            }
            return 1;
        } catch (Exception e) {
            LogUtil.e(e.getMessage(), e);
        }
        return -1;
    }

    /**
     * 解析url附带的参数
     *
     * @param query
     * @return
     */
    public static HashMap<String, String> parseUrlParams(String query) {
        int idx = query.indexOf("?");
        if (idx != -1) {
            query = query.substring(idx + 1);
        }
        HashMap<String, String> result = new HashMap();
        String[] params = query.split("&");
        String[] arrayOfString1;
        int j = (arrayOfString1 = params).length;
        for (int i = 0; i < j; i++) {
            String param = arrayOfString1[i];
            String[] array = param.split("=");
            if (array.length >= 2) {
                try {
                    String value = URLDecoder.decode(array[1], "UTF-8").trim();
                    result.put(array[0].trim(), value);
                } catch (Exception e) {
                    LogUtil.e("解析url参数异常", e);
                }
            }
        }
        return result;
    }
}
