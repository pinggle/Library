package com.hengtiansoft.ecommerce.library.base.util;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：下载任务管理
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class DownloadTaskManager {
    private static DownloadTaskManager manager;
    // private List<String> downloadList;
    private HashMap<Long, String> downloadMap;

    private DownloadTaskManager() {
        // downloadList = new ArrayList<String>();
        downloadMap = new HashMap<Long, String>();
    }

    public static DownloadTaskManager getInstance() {
        synchronized (DownloadTaskManager.class) {
            if (manager == null) {
                manager = new DownloadTaskManager();
            }
        }
        return manager;
    }

    public synchronized boolean addTask(long id, String url) {
        boolean sucess = false;
        if (!TextUtils.isEmpty(url)) {
            if (!contains(url)) {
                // downloadList.add(url);
                downloadMap.put(id, url);
                sucess = true;
            }
        }
        return sucess;
    }

    /**
     * 判断当前地址是否正在下载中
     *
     * @param url
     * @return
     */
    public synchronized boolean getTask(String url) {
        boolean sucess = false;
        if (!TextUtils.isEmpty(url)) {
            if (contains(url)) {
                sucess = true;
            }
        }
        return sucess;
    }

    public synchronized String getUrl(Long id) {
        return downloadMap.get(id);
    }

    /**
     * 下载完成后从下载队列中删除该文件
     *
     * @param id
     */
    public synchronized boolean delTask(long id) {
        if (containsKey(id)) {
            // downloadList.remove(downloadMap.get(id));
            downloadMap.remove(id);
            return true;
        }
        return false;
    }

    private boolean contains(Object obj) {
        for (Object o : downloadMap.values()) {
            if (o.equals(obj)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsKey(Object obj) {
        for (Object o : downloadMap.keySet()) {
            if (o.equals(obj)) {
                return true;
            }
        }
        return false;
    }
}
