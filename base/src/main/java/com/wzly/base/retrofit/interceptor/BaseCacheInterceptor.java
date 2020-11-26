package com.wzly.base.retrofit.interceptor;


import android.util.ArrayMap;

import com.wzly.base.utils.NetworkUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/14
 *     desc   : 缓存拦截器
 *     version: 1.0
 * </pre>
 */
public abstract class BaseCacheInterceptor implements Interceptor {

    public static final long DEFAULT_NETWORK_CACHE_MAX_AGE = 60;

    /**
     * 根据网络缓存时效 获取缓存 , Request 不需要处理
     */
    public static final int CACHE_CONTROL_NETWORK_VALIDITY = -1;

    /**
     * 默认 ： 有网络取网络，无网络取缓存
     */
    public static final int CACHE_CONTROL_DEFAULT = 0;
    /**
     * 强行读取缓存数据 ： 存在没缓存情况
     */
    public static final int CACHE_CONTROL_FORCE_CACHE = 1;
    /**
     * 强行读取网络 : 存在没网络情况
     */
    public static final int CACHE_CONTROL_FORCE_NETWORK = 2;


    /**
     * 有网络时 缓存时间
     * 单位 s
     */
    private long cacheMaxAge;

    /**
     * 网络缓存控制
     */
    private int cache;

    public BaseCacheInterceptor(int cache, long cacheMaxAge) {
        this.cache = cache;
        this.cacheMaxAge = cacheMaxAge;
    }

    /**
     * 无网络缓存时效， 默认 24 小时
     */
    protected int getCacheMaxStaleHours() {
        return 24;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        boolean netAvailable = NetworkUtil.isConnected();

        Request.Builder requestBuilder = request.newBuilder();

        switch (cache) {
            case CACHE_CONTROL_FORCE_CACHE:
                request = requestBuilder
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                break;
            case CACHE_CONTROL_FORCE_NETWORK:
                request = requestBuilder
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
                break;
            case CACHE_CONTROL_DEFAULT:
                request = requestBuilder
                        .cacheControl(netAvailable ?
                                CacheControl.FORCE_NETWORK :
                                CacheControl.FORCE_CACHE)
                        .build();
                break;
            default:
                //CACHE_CONTROL_NETWORK_VALIDITY
                //不需要重写，默认会根据网络时效是否请求接口；假如没有网络，需设置读取缓存
                if (!netAvailable) {
                    request = requestBuilder
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                break;
        }

        Response response = chain.proceed(request);
        if (netAvailable) {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    // 有网络时 设置缓存时效 为一分钟
                    .header("Cache-Control", "public, max-age=" + cacheMaxAge)
                    .build();
        } else {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    // 无网络时，设置超时为 1 天
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + getCacheMaxStaleHours() * 60 * 60)
                    .build();
        }
        return response;
    }

    protected Map<String, String> getRequestParams(Request request) {
        Map<String, String> map = new ArrayMap<>();
        if ("GET".equals(request.method())) {
            HttpUrl.Builder urlBuilder = request.url().newBuilder();
            HttpUrl httpUrl = urlBuilder.build();
            Set<String> paramKeys = httpUrl.queryParameterNames();
            for (String key : paramKeys) {
                String value = httpUrl.queryParameter(key);
                map.put(key, value);
            }
        } else if ("POST".equals(request.method())) {
            if (request.body() instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                for (int i = 0; i < formBody.size(); i++) {
                    map.put(formBody.name(i), formBody.value(i));
                }
            }
        }
        return map;
    }
}
