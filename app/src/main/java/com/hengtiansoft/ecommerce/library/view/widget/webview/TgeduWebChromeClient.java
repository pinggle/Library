package com.hengtiansoft.ecommerce.library.view.widget.webview;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.hengtiansoft.ecommerce.library.base.util.LogUtil;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.view.widget.webview
 * Description：
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class TgeduWebChromeClient extends WebChromeClient {
    private TgeduWebViewLayout m_vWebView;
    private TgeduWebViewProgressListener progressListener;

    public TgeduWebChromeClient(TgeduWebViewLayout vWebView) {
        this.m_vWebView = vWebView;
        this.progressListener = vWebView.getProgressListener();
    }

    /**
     * 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
     */
    private DialogInterface.OnKeyListener m_pOnKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
            return true;
        }
    };

    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
        return super.onCreateWindow(view, dialog, userGesture, resultMsg);
    }

    /**
     * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////�? ?message=内容&type=1&button=启用,|取消,|&id=&
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        try {
//            new MyDialog(view.getContext(), view.getContext().getResources().getString(R.string.dialog_title), message, MyDialog.Dialog_Type_Yes, null, null);
            result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容�?
        } catch (Exception e) {
            LogUtil.e(e.getMessage(), e);
        }
        return true;
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return super.onJsBeforeUnload(view, url, message, result);
    }

    /**
     * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////�? message约定：提示信息�?操作类型1华西启用栏目、栏目ID
     * ?message=内容&type=1&button=启用&id=&
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
//        try {
//            new MyDialog(view.getContext(), view.getContext().getResources().getString(R.string.dialog_title),
//                    message, MyDialog.Dialog_Type_YesNo, new MyDialog.OnClickYesListener() {
//                public void onClickYes() {
//                    result.confirm();
//                }
//            }, new MyDialog.OnClickNoListener() {
//                public void onClickNo() {
//                    result.cancel();
//                }
//            });
//        } catch (Exception e) {
//            LogUtil.e(e.getMessage(), e);
//        }
        return true;
    }

    /**
     * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////�? window.prompt('请输入您的域名地�?, '618119.com');
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {

//        new MyDialog(view.getContext(), view.getContext().getResources().getString(R.string.dialog_title),
//                message, MyDialog.Dialog_Type_YesNo, new MyDialog.OnClickYesListener() {
//            public void onClickYes() {
//                result.confirm("");
//            }
//        }, new MyDialog.OnClickNoListener() {
//            public void onClickNo() {
//                result.cancel();
//            }
//        });

        return true;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        LogUtil.d("-----onProgressChanged----- newProgress=" + newProgress);
        if (m_vWebView != null && progressListener != null) {
            if (newProgress <= 100) {
                progressListener.setProgress(newProgress);
            }
        }

    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }

    @Override
    public void onRequestFocus(WebView view) {
        super.onRequestFocus(view);
    }

}
