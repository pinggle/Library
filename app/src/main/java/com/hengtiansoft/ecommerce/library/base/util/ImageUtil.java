package com.hengtiansoft.ecommerce.library.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.util.helper.GlideCircleTransform;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.base.util
 * Description：图片加载工具类
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class ImageUtil {

    /**
     * 加载普通图片
     *
     * @param v
     * @param url
     */
    public static void loadImg(ImageView v, String url) {
        Glide.with(v.getContext())
                .load(getFuckUrl(url))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(v);

    }

    /**
     * 加载圆形图片
     *
     * @param v
     * @param url
     */
    public static void loadRoundImg(ImageView v, String url) {
        Glide.with(v.getContext())
                .load(getFuckUrl(url))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(v.getContext()))
                .error(R.mipmap.ic_launcher)
                .into(v);
    }

    /**
     * 获取图片的url
     *
     * @param url
     * @return
     */
    public static String getFuckUrl(String url) {
        if (url != null && url.startsWith("http://ear.duomi.com/wp-content/themes/headlines/thumb.php?src=")) {
            url = url.substring(url.indexOf("=") + 1, url.indexOf("jpg") > 0 ? url.indexOf("jpg") + 3 : url.indexOf
                    ("png") > 0 ? url.indexOf("png") + 3 : url.length());
            url = url.replace("kxt.fm", "ear.duomi.com");
        }
        return url;
    }

    /**
     * 保存图像文件
     *
     * @param bm       bitmap
     * @param fileName 文件名
     * @throws IOException
     */
    public static String saveFile(Bitmap bm, String fileName) {
        String rootPath = Environment.getExternalStorageDirectory().toString();
        File dirFile = new File(rootPath + "/DCIM/Camera/");
        if (!dirFile.exists()) {
            dirFile.mkdir();// 创建文件夹
        }
        File myCaptureFile = new File(rootPath + "/DCIM/Camera/" + fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        } catch (FileNotFoundException e) {
            LogUtil.e("找不到文件异常", e);
        }
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 高质量压缩
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
            LogUtil.e("输入输出异常", e);
        }
        return myCaptureFile.getAbsolutePath();
    }

    /**
     * 获得指定文件的byte数组
     *
     * @param file 指定文件
     * @return
     */
    public static byte[] getBytes(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            LogUtil.e("输入输出异常", e);
        }
        return buffer;
    }

    /**
     * 获取图片的相关尺寸信息（宽，高，左边距，下边距）
     *
     * @param context
     * @param imgName
     * @return
     */
    public static int[] getImageSize(Context context, String imgName) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();

        int nBodyHeight = dm.heightPixels;
        int nBodyWidht = dm.widthPixels;
        int nResID = ResourcesUtil.getDrawabelID(context, imgName);
        int finalImageWidth = 0, finalImageHeight = 0;
        if (finalImageWidth == 0) {
            int imageHeight = context.getResources().getDrawable(nResID).getIntrinsicHeight();// 一行的高度
            int imageWidht = context.getResources().getDrawable(nResID).getIntrinsicWidth();
            finalImageHeight = nBodyHeight;
            finalImageWidth = finalImageHeight * (imageWidht / imageHeight);// 按比例

            if (finalImageWidth < nBodyWidht) {
                float f = nBodyWidht / finalImageWidth;
                finalImageWidth = nBodyWidht;
                finalImageHeight = (int) (finalImageHeight * f);
            }
        }
        return new int[]{finalImageWidth, finalImageHeight, 0, (dm.heightPixels - finalImageHeight) / 2};
    }
}
