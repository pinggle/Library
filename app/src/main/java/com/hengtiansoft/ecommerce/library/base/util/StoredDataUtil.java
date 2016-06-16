package com.hengtiansoft.ecommerce.library.base.util;

import android.content.Context;
import android.text.TextUtils;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：基于SharedPreferences,每次打开时,根据上次打开时记录的版本即可区分此次打开的情形。
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class StoredDataUtil {

    public static final int LMODE_NEW_INSTALL = 1; // 启动-模式,首次安装-首次启动
    public static final int LMODE_UPDATE = 2;// 覆盖安装-首次启动
    public static final int LMODE_AGAIN = 3;// 已安装-二次启动

    private boolean isOpenMarked = false;
    private int launchMode = LMODE_AGAIN; // 启动-模式
    private static StoredDataUtil INSTANCE;


    public static synchronized StoredDataUtil getInstance() {
        if (INSTANCE == null)
            INSTANCE = new StoredDataUtil();

        return INSTANCE;
    }


    // 标记-打开app,用于产生-是否首次打开
    public int markOpenApp(Context mContext) {

        String lastVersion = SharedPreferencesUtil.getVersionCode();
        String curVersion = ApplicationUtil.getVersion(mContext).get(ApplicationUtil.VERSION_CODE);

        // 首次启动
        if (TextUtils.isEmpty(lastVersion)) {
            launchMode = LMODE_NEW_INSTALL;
            SharedPreferencesUtil.setVersionCode(curVersion);
        }
        // 更新
        else if (!curVersion.equals(lastVersion)) {
            launchMode = LMODE_UPDATE;
            SharedPreferencesUtil.setVersionCode(curVersion);
        }
        // 二次启动(版本未变)
        else {
            launchMode = LMODE_AGAIN;
        }
        return launchMode;

    }

    public int getLaunchMode() {
        return launchMode;
    }

    // 首次打开,新安装、覆盖(版本号不同)
    public boolean isFirstOpen() {
        return launchMode != LMODE_AGAIN;
    }

}
