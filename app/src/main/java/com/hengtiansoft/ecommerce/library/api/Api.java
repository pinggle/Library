package com.hengtiansoft.ecommerce.library.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hengtiansoft.ecommerce.library.BasicApplication;
import com.hengtiansoft.ecommerce.library.BuildConfig;
import com.hengtiansoft.ecommerce.library.base.util.LogUtil;
import com.hengtiansoft.ecommerce.library.base.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ProjectName：Library
 * PackageName: com.hengtiansoft.ecommerce.library.api
 * Description：基于retrofit封装的网络请求框架
 *
 * @author liminghuang
 * @time 6/14/2016 11:03
 * Modifier：liminghuang
 * ModifyTime：6/14/2016 11:03
 * Comment：
 */
public class Api {

    public static final String X_LC_Id = "i7j2k7bm26g7csk7uuegxlvfyw79gkk4p200geei8jmaevmx";
    public static final String X_LC_Key = "n6elpebcs84yjeaj5ht7x0eii9z83iea8bec9szerejj7zy3";
    public static final String BASE_URL = "https://leancloud.cn:443/1.1/";
    public static final long DEFAULT_TIMEOUT = 7676l;

    public Retrofit retrofit;
    // 网络请求接口包装类，可细分多个Service
    public ApiService apiService;

    // 在访问HttpMethods时创建延迟加载的单例
    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    // 获取单例
    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Api() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            // http请求Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);// 日志记录
        }
        // 网络请求的缓存机制，无网络也能显示数据
        File cacheFile = new File(BasicApplication.getAppContext().getCacheDir(), "ecommercecache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        builder.addNetworkInterceptor(new HttpCacheInterceptor());// 请求缓存

        // 保持请求是同一个cookie
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        // 其他设置
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)// 错误重连
                .addInterceptor(headerInterceptor)// 请求头
//                .addInterceptor(addQueryParameterInterceptor)// 公共参数
                .cache(cache)// 缓存目录
                .cookieJar(new JavaNetCookieJar(cookieManager));

        OkHttpClient okHttpClient = builder.build();
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
//                .addInterceptor(headerInterceptor)// 请求头
//                .addInterceptor(loggingInterceptor)// 日志记录
//                .addNetworkInterceptor(new HttpCacheInterceptor())// 请求缓存
//                .cache(cache)// 缓存目录
//                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)// okhttp的实现
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())// RxJava支持
                .addConverterFactory(GsonConverterFactory.create(gson))// Gson支持
                .baseUrl(BASE_URL)// 请求地址
                .build();

        apiService = retrofit.create(ApiService.class);// 可使用ServiceFactory细分接口服务
    }

    // 添加请求头
    Interceptor headerInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
            .addHeader("X-LC-Id", X_LC_Id)
            .addHeader("X-LC-Key", X_LC_Key)
            .addHeader("Content-Type", "application/json")
            .build());

    // 添加接口的公共参数,即url问号后面附带的参数
    Interceptor addQueryParameterInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            Request request;
            String method = originalRequest.method();
            Headers headers = originalRequest.headers();
            HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("platform", "android")
                    .addQueryParameter("version", "1.0.0").build();
            request = originalRequest.newBuilder().url(modifiedUrl).build();
            return chain.proceed(request);
        }
    };

    // 缓存实现封装
    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(BasicApplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtil.d(" no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(BasicApplication.getAppContext())) {
                // 有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                // int maxAge = 0;
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)//"public, max-age="+maxAge
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;// 2419200
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}