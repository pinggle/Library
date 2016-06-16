package com.hengtiansoft.ecommerce.library.view.widget.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hengtiansoft.ecommerce.library.R;
import com.hengtiansoft.ecommerce.library.base.util.ReflectUtil;
import com.hengtiansoft.ecommerce.library.base.util.LogUtil;
import com.hengtiansoft.ecommerce.library.base.util.ResourcesUtil;

import java.util.LinkedList;
import java.util.Locale;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.view.widget.webview
 * Description：自定义WebView的容器包装
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class TgeduWebViewLayout extends LinearLayout {

    /**
     * url过滤器
     **/
    private TgeduWebViewDealUrl tgeduWebViewDealUrl;
    /**
     * 记录请求的url
     **/
    private LinkedList<String> mWebViewWithUrl = new LinkedList<String>();
    private Context mContext;
    private ProgressBar mProgressBar;
    private WebView mWebView;
    /**
     * webview父容器
     **/
    private FrameLayout mWebViewParent;
    private TgeduWebViewProgressListener mProgressListener;
    private static final String FIELD_NAME = "mOnlyIndeterminate";

    public TgeduWebViewLayout(Context context, AttributeSet attrs, TgeduWebViewProgressListener progressListener) {
        super(context, attrs);
        this.mContext = context;
        this.mProgressListener = progressListener;
        mWebViewWithUrl.clear();
        this.tgeduWebViewDealUrl = new TgeduWebViewDealUrl(context, this);
        setBackgroundColor(getResources().getColor(R.color.webview_layout_bg));
        initView();
    }

    public TgeduWebViewLayout(Context context, TgeduWebViewProgressListener progressListener) {
        super(context);
        this.mContext = context;
        this.mProgressListener = progressListener;
        mWebViewWithUrl.clear();
        this.tgeduWebViewDealUrl = new TgeduWebViewDealUrl(context, this);
        setBackgroundColor(getResources().getColor(R.color.webview_layout_bg));//Color.parseColor("#F5F4FA")
        setOrientation(LinearLayout.VERTICAL);
        initView();
    }

    public void initView() {
        this.removeAllViews();
        this.addView(newProgressBar(), 0);
        this.addView(newWebView(), 1);
    }

    /**
     * Description: WebView的父容器是FrameLayout，该容器中还需要添加网络请求时显示的页面
     *
     * @return
     */
    public FrameLayout newWebView() {
        LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        mWebViewParent = new FrameLayout(getContext());
        mWebViewParent.setLayoutParams(lParams);

        mWebView = new WebView(getContext()) {
            @Override
            public void loadUrl(String url) {
                if (!TextUtils.isEmpty(url) && !(url.toLowerCase(Locale.US).startsWith("javascript"))) {
                    setTag(url);// 给控件设置附加信息
                    addRecentUrl(url);
                }
                super.loadUrl(url);
            }

            @Override
            protected void onScrollChanged(int l, int t, int oldl, int oldt) {
                // LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mProgressBar.getLayoutParams();
                // lp. = l;
                // lp.y = t;
                // mProgressBar.setLayoutParams(lp);
                super.onScrollChanged(l, t, oldl, oldt);
            }

            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                return super.onKeyDown(keyCode, event);
            }
        };

        lParams.weight = 1;
        mWebView.setLayoutParams(lParams);
        mWebView.setBackgroundColor(getResources().getColor(R.color.webview_layout_bg));
        mWebViewParent.removeAllViews();
        mWebViewParent.addView(mWebView, 0);

        setWebViewParams(mWebView);
        return mWebViewParent;
    }

    /**
     * 新建Progressbar
     *
     * @return
     */
    public ProgressBar newProgressBar() {
        // mProgressBar = new ProgressBar(getContext(), null, ResourcesUtil.getStyleID(getContext(),
        // "ProgressBar_Mini"));


        mProgressBar = new ProgressBar(getContext());
        ReflectUtil.setFieldValue(mProgressBar, FIELD_NAME, new Boolean(false));
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.view_progressbar_mini));
        mProgressBar.setIndeterminateDrawable(getResources().getDrawable(
                android.R.drawable.progress_indeterminate_horizontal));
        mProgressBar.setMinimumHeight(5);
        LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                ResourcesUtil.dip2px(mContext, 5));
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        mProgressBar.setVisibility(View.GONE);
        mProgressBar.setLayoutParams(lParams);
        return mProgressBar;
    }

    /**
     * Description: 加载页面，支持是否清除缓存
     *
     * @param strUrl
     * @param isClearCache
     */
    public void loadUrl(String strUrl, boolean isClearCache) {
        if (strUrl == null || strUrl.length() <= 0) {
            return;
        }
        if (isClearCache) {
            LogUtil.d("isClearCache: " + isClearCache);
            clearCache();
        }
        LogUtil.d("loadUrl: " + strUrl);
        mWebView.loadUrl(strUrl);
    }

    /**
     * Description: 记录除去参数的url地址
     *
     * @param strUrl
     * @return
     */
    public boolean addRecentUrl(String strUrl) {
        if (mWebViewWithUrl == null) {
            mWebViewWithUrl = new LinkedList<String>();
            mWebViewWithUrl.clear();
        }

        int nWenHaoPos = strUrl.indexOf("?");

        if (nWenHaoPos > 0) {
            strUrl = strUrl.substring(0, nWenHaoPos);
        }

        if (mWebViewWithUrl.size() <= 0) {
            mWebViewWithUrl.addLast(strUrl);
        } else if (mWebViewWithUrl.indexOf(strUrl) < 0) {
            mWebViewWithUrl.addLast(strUrl);
        } else {
            mWebViewWithUrl.remove(strUrl);
            mWebViewWithUrl.addLast(strUrl);
        }

        return true;
    }

    /**
     * Description: 设置Webview的相关参数
     *
     * @param webview
     */
    private void setWebViewParams(WebView webview) {
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先使用缓存
        webview.getSettings().setJavaScriptEnabled(true);// 支持javaScript，才能过滤url
        webview.setWebViewClient(new TgeduWebViewClient(this));
        webview.setWebChromeClient(new TgeduWebChromeClient(this));
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                LogUtil.d("WebViewDownLoad Detail:url=" + url + ", userAgent=" + userAgent + ", contentDisposition="
                        + contentDisposition
                        + ", mimetype=" + mimetype + ", contentLength=" + contentLength);
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (mContext != null) {
                    mContext.startActivity(intent);
                }
            }
        });
        webview.requestFocusFromTouch();
        webview.requestFocus();
        webview.setScrollbarFadingEnabled(false);
        webview.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                    }
                    break;
                }
                return false;
            }
        });
    }

    /**
     * Description: 清除webview缓存
     */
    private void clearCache() {
        mWebView.clearCache(true);
    }

    public TgeduWebViewDealUrl getTgeduWebViewDealUrl() {
        return tgeduWebViewDealUrl;
    }

    public LinkedList<String> getWebViewWithUrl() {
        return mWebViewWithUrl;
    }

    public Context getmContext() {
        return mContext;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public WebView getmWebView() {
        return mWebView;
    }

    public TgeduWebViewProgressListener getProgressListener() {
        return mProgressListener;
    }

    public void setProgressListener(TgeduWebViewProgressListener mProgressListener) {
        this.mProgressListener = mProgressListener;
    }

    public FrameLayout getWebViewParent() {
        return mWebViewParent;
    }
}
