package com.hengtiansoft.ecommerce.library.view.widget.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.hengtiansoft.ecommerce.library.base.util.DataFormatUtil;
import com.hengtiansoft.ecommerce.library.base.util.LogUtil;
import com.hengtiansoft.ecommerce.library.base.util.UrlUtil;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.view.widget.webview
 * Description：对于自定义协议的统一处理
 *
 * @author liminghuang
 * @time 6/15/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/15/2016 11:03
 * Comment：
 */
public class TgeduWebViewDealUrl {
    private Context context;
    private TgeduWebViewLayout m_vWebView;

    public TgeduWebViewDealUrl(Context context, TgeduWebViewLayout vWebView) {
        setContext(context);
        m_vWebView = vWebView;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * Description: url过滤器并执行相应的功能
     *
     * @param strUrl
     */
    public void onWebClientUrlLis(String strUrl) {
        LogUtil.d("onWebClientUrlLis URL=" + strUrl);

        if (strUrl == null || strUrl.length() <= 0) {
            return;
        }

        String tempUrl = strUrl.toLowerCase(Locale.US);

        final String nWenHao = "?";
        int nWenHaoPos = strUrl.indexOf(nWenHao);
        Map<String, String> valueMap = new HashMap<String, String>();

        if (nWenHaoPos > 0) {
            nWenHaoPos = nWenHaoPos + 1;
            String urlParam = strUrl;
            if (urlParam.endsWith("/")) {
                urlParam = urlParam.substring(nWenHaoPos, urlParam.length() - 1);
            } else {
                urlParam = urlParam.substring(nWenHaoPos, urlParam.length());
            }

            UrlUtil.GetMapValue(urlParam, null, valueMap, "&", true/* false */);
        }

        // 执行url请求的功能
        if (IsOpenOtherBrowser(strUrl, valueMap)) {
            return;
        } else if (IsSkipToActivity(strUrl, valueMap)) {
            return;
        } else {
            if (!tempUrl.trim().equals("about:blank")) {
                m_vWebView.loadUrl(strUrl, true);
            }
        }
    }

    /**
     * Description: 跳转带有返回箭头的Activity
     *
     * @param strUrl
     * @param urlParamMap
     * @return
     */
    public boolean IsSkipToActivity(String strUrl, Map<String, String> urlParamMap) {
        final String strUrlStart = "http://skip/";
        if (strUrl != null && strUrl.length() > 0 && strUrl.startsWith(strUrlStart)) {
            strUrl = URLDecoder.decode(strUrl);// 中文需要反序列化，即URL解码
            strUrl = getValueByUrl(strUrl, "url");
            Intent intent = new Intent();
            //TODO FIXME
//            intent.setClass(m_vWebView.getContext(), HonorListActivity.class);
            intent.putExtra("url", strUrl);
            intent.putExtra("title", URLDecoder.decode(urlParamMap.get("title")));
            m_vWebView.getContext().startActivity(intent);

            return true;
        }

        return false;
    }

    /**
     * 使用系统/第三方浏览器打开
     *
     * @param strUrl
     * @param urlParamMap
     * @return
     */
    public boolean IsOpenOtherBrowser(String strUrl, Map<String, String> urlParamMap) {
        String strUrlStart = "http://browseropen:";
        if (strUrl != null && strUrl.length() > 0 && strUrl.startsWith(strUrlStart)) {
            try {
                strUrl = URLDecoder.decode(strUrl);// 中文需要反序列化，即URL解码
                strUrl = getValueByUrl(strUrl, "url");
                Uri uri = Uri.parse(strUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            } catch (Exception e) {
                LogUtil.e("无法解析出url地址", e);
            }
            return true;
        }

        return false;
    }

    /**
     * Description: 获取url中的key="url"的值
     *
     * @param url
     * @param param 主要是"url"
     * @return
     */
    public String getValueByUrl(String url, String param) {
        String value = "";
        String tempParam = param + "=";
        if (url != null && url.indexOf(tempParam) >= 0) {
            // Base64解密
            url = DataFormatUtil.decryptBase64(url.substring(url.indexOf(tempParam) + tempParam.length(), url.length
                    ()));

            int splitpos = url.indexOf("&");
            if (splitpos < 0)
                splitpos = url.length();
            value = url.substring(0, splitpos);
        }
        return value;
    }

    public Map<String, String> onDealParamsWithAction(int nAction, String strUrl) {
        Map<String, String> ayRetMap = new HashMap<String, String>();
        ayRetMap.clear();

        if (strUrl != null && strUrl.length() > 0) {
            UrlUtil.GetMapValue(strUrl, null, ayRetMap, "&&", true/* false */);
        }

        if (ayRetMap.size() > 0) {
            Map<String, String> tempMap = new HashMap<String, String>();
            tempMap.clear();
            for (Entry<String, String> entry : ayRetMap.entrySet()) {
                tempMap.put(entry.getKey(), /* URLDecoder.decode(entry.getValue()) */Uri.decode(entry.getValue()));
            }
            ayRetMap.clear();
            ayRetMap = tempMap;
        } else {
            return ayRetMap;
        }

        return ayRetMap;
    }

}
