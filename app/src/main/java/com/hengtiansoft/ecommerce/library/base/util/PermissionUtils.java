package com.hengtiansoft.ecommerce.library.base.util;
/*
    Copyright 2015 ChangXing kingh.cha@gmail.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：android6.0权限管理工具类
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class PermissionUtils {

    public static final int PERMISSION_REQUEST_CODE = 2;
    public static final int PERMISSION_SETTING_REQ_CODE = 0x1000;

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermission(Object cxt, String permission, int requestCode) {
        if (!checkSelfPermissionWrapper(cxt, permission)) {
            if (!shouldShowRequestPermissionRationaleWrapper(cxt, permission)) {
                requestPermissionsWrapper(cxt, new String[]{permission}, requestCode);
            } else {
                LogUtil.d("should show rational");
            }
            return false;
        }

        return true;
    }


    private static void requestPermissionsWrapper(Object cxt, String[] permission, int requestCode) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            ActivityCompat.requestPermissions(activity, permission, requestCode);
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            fragment.requestPermissions(permission, requestCode);
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }


    private static boolean shouldShowRequestPermissionRationaleWrapper(Object cxt, String permission) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            return ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission);
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            return fragment.shouldShowRequestPermissionRationale(permission);
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }

    @TargetApi(23)
    private static boolean checkSelfPermissionWrapper(Object cxt, String permission) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            return ActivityCompat.checkSelfPermission(activity,
                    permission) == PackageManager.PERMISSION_GRANTED;
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            return fragment.getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }
    }

    private static String[] checkSelfPermissionArray(Object cxt, String[] permission) {
        ArrayList<String> permiList = new ArrayList<>();
        for (String p : permission) {
            if (!checkSelfPermissionWrapper(cxt, p)) {
                permiList.add(p);
            }
        }

        return permiList.toArray(new String[permiList.size()]);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermissionArray(Object cxt, String[] permission, int requestCode) {
        String[] permissionNo = checkSelfPermissionArray(cxt, permission);
        if (permissionNo.length > 0) {
            requestPermissionsWrapper(cxt, permissionNo, requestCode);
            return false;
        } else {
            return true;
        }
    }

    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测系统弹出权限
     *
     * @param cxt
     * @param req
     * @return
     */
    @TargetApi(23)
    public static boolean checkSettingAlertPermission(Object cxt, int req) {
        if (cxt instanceof Activity) {
            Activity activity = (Activity) cxt;
            if (!Settings.canDrawOverlays(activity.getBaseContext())) {
                LogUtil.i("Setting not permission");

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(intent, req);
                return false;
            }
        } else if (cxt instanceof Fragment) {
            Fragment fragment = (Fragment) cxt;
            if (!Settings.canDrawOverlays(fragment.getActivity())) {
                LogUtil.i("Setting not permission");

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + fragment.getActivity().getPackageName()));
                fragment.startActivityForResult(intent, req);
                return false;
            }
        } else {
            throw new RuntimeException("cxt is net a activity or fragment");
        }

        return true;
    }

}
