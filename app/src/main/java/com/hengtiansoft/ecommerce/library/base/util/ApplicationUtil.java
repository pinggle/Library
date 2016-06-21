package com.hengtiansoft.ecommerce.library.base.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：应用信息管理工具类
 *
 * @author liminghuang
 * @time 6/15/2016 16:31
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 16:31
 * Comment：
 */
public class ApplicationUtil {
    public static final String VERSION_CODE = "versionCode";
    public static final String VERSION_NAME = "versionName";

    /**
     * 获取当前程序的版本号
     *
     * @return
     */
    public static Map<String, String> getVersion(Context context) {
        Map<String, String> versionInfo = new HashMap<>();
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            LogUtil.d(context.getPackageName().toString() + "首次安装时间" + new Date(info.firstInstallTime).toString());
            LogUtil.d(context.getPackageName().toString() + "最近一次更新时间" + new Date(info.lastUpdateTime).toString());
            versionInfo.put(VERSION_CODE, String.valueOf(info.versionCode));
            versionInfo.put(VERSION_NAME, info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e("获取包信息异常", e);
        } finally {
            return versionInfo;
        }
    }

    /**
     * 获取设备上的已安装的应用列表
     *
     * @param context
     * @return
     */
    public static List<ApplicationInfo> getApplicationInfos(Activity context) {
        PackageManager packageManager = context.getPackageManager();

        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA);//返回在设备上安装的所有应用程序包的列表。
//        packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);//返回在设备上安装的所有包的列表。
//        packageManager.getActivityInfo();//获取对应组件名的Activity的信息。
    }

    /**
     * 卸载应用
     *
     * @param context
     * @param item
     */
    public static void delete(Activity context, ApplicationInfo item) {
        if (!isHaveApplication(context, getApplicationInfos(context), item.packageName)) {
            LogUtil.d("指定的应用：" + item.packageName + "尙未安装");
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.fromParts("package", item.packageName, null));
            context.startActivity(intent);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    /**
     * 是否存在指定的应用
     *
     * @param context
     * @param installedApplications
     * @param packageName
     */
    public static boolean isHaveApplication(Activity context, List<ApplicationInfo> installedApplications, final
    String packageName) {
        boolean isExist = false;
        if (installedApplications == null || installedApplications.isEmpty() || TextUtils.isEmpty(packageName)) {
            return isExist;
        }

        for (ApplicationInfo appInfo : installedApplications) {
            LogUtil.d("PackageName : " + appInfo.packageName);
            LogUtil.d("Name: " + appInfo.loadLabel(context.getPackageManager()));
            if (appInfo.packageName.equals(packageName)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    /**
     * 打开应用
     *
     * @param context
     * @param item
     */
    public static void open(Activity context, ApplicationInfo item) {

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(item.packageName);
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            String activityPackageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(activityPackageName, className);

            intent.setComponent(componentName);
            context.startActivity(intent);
        }
    }


    /**
     * 获取栈顶的Activity
     *
     * @param context
     * @return
     */
    public static String getTopActivity(Activity context) {
        String className = null;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTaskInfos) {
                ComponentName cn = runningTaskInfo.topActivity;
                className = cn.getClassName();
            }
        }

        return className;
    }

    /**
     * 获取运行的相关信息
     *
     * @param context
     */
    public static void getRunningInfo(Activity context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activityManager.getAppTasks();// 获取任务栈
        }
        activityManager.getRunningAppProcesses();// 获取系统中正在运行的所有的进程的信息。
        activityManager.getDeviceConfigurationInfo();//获取设备的配置属性。
    }
}
