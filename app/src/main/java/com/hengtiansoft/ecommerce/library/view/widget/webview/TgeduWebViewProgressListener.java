package com.hengtiansoft.ecommerce.library.view.widget.webview;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.view.widget.webview
 * Description：WebView进度条反馈监听
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public interface TgeduWebViewProgressListener {
    void StartProgress();

    void StopProgress();

    void setProgress(int progress);
}
