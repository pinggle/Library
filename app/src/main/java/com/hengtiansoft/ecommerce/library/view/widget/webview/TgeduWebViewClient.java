package com.hengtiansoft.ecommerce.library.view.widget.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hengtiansoft.ecommerce.library.Constants;
import com.hengtiansoft.ecommerce.library.base.util.LogUtil;
import com.hengtiansoft.ecommerce.library.base.util.NetWorkUtil;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.view.widget.webview
 * Description：WebView自定义协议处理
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class TgeduWebViewClient extends WebViewClient {

    private Context context;
    private TgeduWebViewLayout m_vCurWebView;
    private TgeduWebViewProgressListener progressListener;

    public TgeduWebViewClient(TgeduWebViewLayout webView) {
        this.m_vCurWebView = webView;
        this.context = m_vCurWebView.getContext();
        this.progressListener = m_vCurWebView.getProgressListener();
    }

    @Override
    /**
     * 返回值是false的时候控制去TgeduWebView中打开，为true表示程序自己处理
     */
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (!NetWorkUtil.isNetConnected(context)) {
//            new MyDialog(view.getContext(), context.getResources().getString(R.string.dialog_title), context
// .getResources()
//                    .getString(R.string.no_net_tips), MyDialog.Dialog_Type_Yes, constructYesListener(context),
//                    constructNoListener(context));

            return true;
        }

        if (m_vCurWebView != null) {
            if (url.startsWith(Constants.HttpConstant.HTTP_PROTOCOL) && !url.startsWith("http://skip/")) {
                return super.shouldOverrideUrlLoading(view, url); // 跳转至手机浏览器
            }
            // 拦截Url并处理响应业务
            m_vCurWebView.getTgeduWebViewDealUrl().onWebClientUrlLis(url);
        }
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        LogUtil.d("-----onPageStarted-----");
        if (m_vCurWebView != null && progressListener != null) {
            progressListener.StartProgress();
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        LogUtil.d("-----onLoadResource----- url=" + url);
        // if (url.contains(".css?") || url.contains(".js?")) {
        // int nWenHao = url.indexOf("?");
        // if (nWenHao > 0)
        // url = url.substring(0, nWenHao);
        // Log.i(TAG, "-----onLoadResource----- afterUrl=" + url);
        // }

        super.onLoadResource(view, url);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        LogUtil.d("-----onReceivedHttpAuthRequest-----");
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        LogUtil.d("-----onPageFinished-----");
        if (m_vCurWebView != null) {
            m_vCurWebView.scrollTo(0, 0);
        }
        if (m_vCurWebView != null && progressListener != null) {
            progressListener.StopProgress();
        }
        super.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        LogUtil.d("-----onReceivedError----- errorCode=" + errorCode);
        super.onReceivedError(view, errorCode, description, failingUrl);
        // view.loadDataWithBaseURL(null, "<span style=\'color:#FF0000\'>服务器维护，稍后重试....</span>", "text/html", "utf-8",
        // null);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        LogUtil.d("-----onReceivedSslError-----");
        // handler.cancel(); 默认的处理方式，WebView变成空白页
        handler.proceed();// 如果只是简单的接受所有证书的话，就直接调process()方法就行了
        shouldOverrideUrlLoading(view, view.getUrl());
        // handleMessage(Message msg); 其他处理
        // super.onReceivedSslError(view, handler, error);
    }

//    private MyDialog.OnClickYesListener constructYesListener(final Context context) {
//        return new MyDialog.OnClickYesListener() {
//            public void onClickYes() {
//                context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
//            }
//        };
//    }
//
//    private MyDialog.OnClickNoListener constructNoListener(final Context context) {
//        return new MyDialog.OnClickNoListener() {
//            public void onClickNo() {
//            }
//        };
//    }

}
