package com.hengtiansoft.ecommerce.library.base.util;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.hengtiansoft.ecommerce.library.R;

import java.io.File;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：下载工具类
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class DownloadUtil {
    /**
     * TgeduApk/TGEDU.apk
     **/
    public String FilePath;
    public BroadcastReceiver receiver;
    public DownLoadListener downloadListener;
    public Object obj1, obj2;
    private DownloadTaskManager manager;
    private Context context;

    public DownloadUtil(DownLoadListener ownloadListener) {
        this.downloadListener = ownloadListener;
        manager = DownloadTaskManager.getInstance();
    }

    /**
     * 获取可用的完整文件路径
     *
     * @return
     */
    public static String getRootPath() {
        String rootPath;
        if (SDCardUtils.isSDCardEnable()) {
            rootPath = SDCardUtils.getSDCardPath();
        } else {
            rootPath = SDCardUtils.getRootDirectoryPath();
        }
        return rootPath;
    }

    /**
     * apk下载任务
     *
     * @param context    上下文
     * @param url        下载地址
     * @param folderName 存储文件夹名
     * @param bookName   存储文件名
     * @param NotiDesc   通知栏任务描述
     */
    public void downloadApk(final Context context, final String url, final String folderName, final String bookName,
                            final String NotiDesc) {
        if (!manager.getTask(url)) {// 新下载任务
            // 注册广播监听下载完成
            if (receiver == null) {
                receiver = new DownloadCompleteReceiver();
                context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }

            // 先检测SD卡是否存在
            if (!SDCardUtils.isSDCardEnable()) {
                ToastUtil.show("抱歉，SD卡未安装，不能下载！", Toast.LENGTH_SHORT);
                return;
            }

            // 创建文件夹
            File files1 = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + folderName);
            ///storage/emulated/0/folderName
            if (!files1.exists()) {
                LogUtil.w("create path:" + files1.toString());
                files1.mkdirs();
            }
            // 判断本地是否存在,如果存在就删除
            String temp = files1.getPath();
            if (!temp.endsWith(File.separator)) {
                temp = temp + File.separator;// /storage/emulated/0/folderName/
            }
            // apk文件完成路径
            String apkFiles = temp + bookName + ".apk"; // /storage/emulated/0/folderName/bookName.apk
            File files2 = new File(apkFiles);
            if (files2.exists()) {// 已存在则删除旧文件
                files2.delete();
            }

            // apk所在目录
            FilePath = folderName + bookName + ".apk";

            // 截取文件名
            // String fileName = url.substring(url.lastIndexOf("/") + 1);
            // fileName = URLDecoder.decode(fileName);

            // 系统下载服务类
            DownloadManager downManager = (DownloadManager) context.getSystemService(Activity.DOWNLOAD_SERVICE);
            DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));

            // down.setShowRunningNotification(true);
            down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            down.allowScanningByMediaScanner();// 表示允许MediaScanner扫描到这个文件，默认不允许。
            // 在通知栏显示，设为可见可管理
            down.setVisibleInDownloadsUi(true);
            down.setTitle(context.getString(R.string.app_name));
            down.setDescription(NotiDesc);
            down.setMimeType("application/vnd.android.package-archive");
            // 输出目录
            down.setDestinationInExternalPublicDir(folderName, bookName + ".apk"); //
            // /storage/emulated/0/folderName/bookName.apk

            // 将任务加入到下载队列执行
            manager.addTask(downManager.enqueue(down), url);
            downloadListener.start();
        } else {
            ToastUtil.show("正在下载中...", Toast.LENGTH_SHORT);
        }
    }

    public void unregisterReceiver(Context context) throws IllegalArgumentException {
        context.unregisterReceiver(receiver);
    }

    /**
     * 监听下载完成
     *
     * @author Administrator
     */
    public class DownloadCompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // 获取文件路径
                File files = new File(FilePath);
                if (!manager.delTask(intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0))) {
                    // context.unregisterReceiver(receiver);
                    return;
                }
                if (FilePath == null) {
                    // context.unregisterReceiver(receiver);
                    return;
                }
                if (!FilePath.endsWith(".apk") && FilePath.lastIndexOf(".") != -1) {// 是个文件但不是apk文件
                    downloadListener.complete(FilePath, "文件格式不正确", files);
                    return;

                    // File f = new File(Environment.getExternalStorageDirectory().getPath() + "/" + FilePath);
                    // FilePath = FilePath.substring(0, FilePath.lastIndexOf("."));
                    // File targetFile = new File(Environment.getExternalStorageDirectory().getPath() + "/" + FilePath);
                    // if (targetFile.exists()) {
                    // targetFile.delete();
                    // }
                    // f.renameTo(targetFile);
                }
                String path = Environment.getExternalStorageDirectory().getPath() + File.separator + FilePath;//
                // /storage/emulated/0/folderName/bookName.apk

                LogUtil.w(files.toString());
                if ("application/vnd.android.package-archive".equals(getMIMEType(files))) {// apk安装
                    Intent fileIntent = new Intent(Intent.ACTION_VIEW);
                    fileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    fileIntent.setDataAndType(/* Uri.fromFile(files) */Uri.parse("file://" + path),
                            "application/vnd.android.package-archive");
                    context.startActivity(fileIntent);
                }
            }
        }
    }

    public interface DownLoadListener {// 接口中的方法就是回调方法

        void complete(String FilePath, Object obj1, Object obj2);

        void start();
    }

    public static Intent getFileIntent(File file) {
        Uri uri = Uri.fromFile(file);
        String type = getMIMEType(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!type.equals("application/vnd.android.package-archive")) {
            intent.addCategory("android.intent.category.DEFAULT");
        }
        intent.setDataAndType(uri, type);
        return intent;
    }

    private static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

        /* 依扩展名的类型决定MimeType */
        if (end.equals("pdf")) {
            type = "application/pdf";//
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf")
                || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg")
                || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
            type = "application/vnd.android.package-archive";
        } else {
            // /*如果无法直接打开，就跳出软件列表给用户选择 */
            type = "*/*";
        }
        return type;
    }
}
