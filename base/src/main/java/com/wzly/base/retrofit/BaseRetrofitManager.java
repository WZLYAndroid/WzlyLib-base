package com.wzly.base.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import com.wzly.base.retrofit.constant.HttpCacheConstant;
import com.wzly.base.retrofit.consumer.HttpBeanConsumer;
import com.wzly.base.retrofit.consumer.HttpErrorConsumer;
import com.wzly.base.retrofit.consumer.HttpJsonConsumer;
import com.wzly.base.retrofit.json.BooleanTypeAdapter;
import com.wzly.base.retrofit.json.DoubleTypeAdapter;
import com.wzly.base.retrofit.json.FloatTypeAdapter;
import com.wzly.base.retrofit.json.IntegerTypeAdapter;
import com.wzly.base.retrofit.json.ListTypeAdapter;
import com.wzly.base.retrofit.json.LongTypeAdapter;
import com.wzly.base.retrofit.json.StringTypeAdapter;
import com.wzly.base.retrofit.listener.OnRequestCallBack;
import com.wzly.base.retrofit.result.BaseResult;
import com.wzly.base.utils.AppUtil;
import com.wzly.base.retrofit.interceptor.BaseCacheInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/17
 *     desc   : 接口调用管理器
 *     version: 1.0
 * </pre>
 */
public abstract class BaseRetrofitManager<A extends BaseApiService> {

    private A mGeneralApiService;
    private A mForceCacheApiService;
    private A mForceNetWorkApiService;
    private A mForceCacheAndRequestAgainApiService;
    private A mTimeLimitNetAndCacheApiService;

    /**
     * 需要假数据的地址
     */
    protected List<String> virtualUrlList = new ArrayList<>();

    /**
     * 缓存的最大尺寸10m
     */
    protected static Cache mCache = new Cache(new File(AppUtil.getContext().getCacheDir(),
            "response"), 1024 * 1024 * 10);

    {
        configurationRapList();
    }

    /**
     * 手动配置 假数据测试地址
     */
    protected abstract void configurationRapList();

    /**
     * 主动将 url 添加入 假数据测试地址
     */
    protected void addRapList(String url) {
        if (!virtualUrlList.contains(url)) {
            virtualUrlList.add(url);
        }
    }

    /**
     * 将所有的 ApiService 重置为空
     */
    protected void resetApiService() {
        mGeneralApiService = null;
        mForceCacheApiService = null;
        mForceNetWorkApiService = null;
        mForceCacheAndRequestAgainApiService = null;
        mTimeLimitNetAndCacheApiService = null;
    }

    /**
     * 大众接口，直接调用
     */
    public A getApiService() {
        return AppUtil.isAppDebug(AppUtil.getContext()) ?
                getNewApiService(HttpCacheConstant.NET_AND_CACHE, BaseCacheInterceptor.DEFAULT_NETWORK_CACHE_MAX_AGE, null) :
                getReleaseApiService(HttpCacheConstant.NET_AND_CACHE, BaseCacheInterceptor.DEFAULT_NETWORK_CACHE_MAX_AGE, null);
    }

    /**
     * 特殊要求接口
     */
    public A getApiService(int cache) {
        return AppUtil.isAppDebug(AppUtil.getContext()) ?
                getNewApiService(cache, BaseCacheInterceptor.DEFAULT_NETWORK_CACHE_MAX_AGE, null) :
                getReleaseApiService(cache, BaseCacheInterceptor.DEFAULT_NETWORK_CACHE_MAX_AGE, null);
    }

    /**
     * 特殊要求接口
     */
    public A getApiService(int cache, String url) {
        return AppUtil.isAppDebug(AppUtil.getContext()) ?
                getNewApiService(cache, BaseCacheInterceptor.DEFAULT_NETWORK_CACHE_MAX_AGE, url) :
                getReleaseApiService(cache, BaseCacheInterceptor.DEFAULT_NETWORK_CACHE_MAX_AGE, url);
    }

    /**
     * 特殊要求接口
     */
    public A getApiService(int cache, long cacheTime, String url) {
        return AppUtil.isAppDebug(AppUtil.getContext()) ?
                getNewApiService(cache, cacheTime, url) :
                getReleaseApiService(cache, cacheTime, url);
    }

    /**
     * release 版本获取 RetrofitApiService ； 保存当前 RetrofitApiService 对象，一种缓存策略只创建一次
     */
    private A getReleaseApiService(int cache, long cacheTime, String url) {
        if (cache == HttpCacheConstant.TIME_LIMIT_NET_AND_CACHE) {
            if (mTimeLimitNetAndCacheApiService == null) {
                mTimeLimitNetAndCacheApiService = getNewApiService(cache, cacheTime, url);
            }
            return mTimeLimitNetAndCacheApiService;
        } else if (cache == HttpCacheConstant.FORCE_CACHE_AND_REQUEST_AGAIN) {
            if (mForceCacheAndRequestAgainApiService == null) {
                mForceCacheAndRequestAgainApiService = getNewApiService(cache, cacheTime, url);
            }
            return mForceCacheAndRequestAgainApiService;
        } else if (cache == HttpCacheConstant.FORCE_CACHE) {
            if (mForceCacheApiService == null) {
                mForceCacheApiService = getNewApiService(cache, cacheTime, url);
            }
            return mForceCacheApiService;
        } else if (cache == HttpCacheConstant.FORCE_NETWORK) {
            if (mForceNetWorkApiService == null) {
                mForceNetWorkApiService = getNewApiService(cache, cacheTime, url);
            }
            return mForceNetWorkApiService;
        } else {
            if (mGeneralApiService == null) {
                mGeneralApiService = getNewApiService(cache, cacheTime, url);
            }
            return mGeneralApiService;
        }
    }

    /**
     * 新创一个 Retrofit 来创建 RetrofitApiService
     *
     * @param url       需要调试假数据的 url， 将测试假数据地址添加进集合 {@link #addRapList } or {@link #configurationRapList} 仅适用 debug
     * @param cache     缓存策略 {@link HttpCacheConstant}
     * @param cacheTime 有网络时缓存时间， 目前仅适用于该缓存策略 {@link HttpCacheConstant#TIME_LIMIT_NET_AND_CACHE}
     */
    protected abstract A getNewApiService(int cache, long cacheTime, String url);

    protected Gson buildRetrofitGsonConverterFactory() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(TypeAdapters.newFactory(String.class, new StringTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(boolean.class, Boolean.class, new BooleanTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(int.class, Integer.class, new IntegerTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(long.class, Long.class, new LongTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(float.class, Float.class, new FloatTypeAdapter()))
                .registerTypeAdapterFactory(TypeAdapters.newFactory(double.class, Double.class, new DoubleTypeAdapter()))
                .registerTypeHierarchyAdapter(List.class, new ListTypeAdapter())
                .create();
    }


    public <T extends BaseResult> Disposable requestForBean(Observable<T> mObservable, @Nullable OnRequestCallBack<T> callBack) {
        return mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpBeanConsumer<T>() {
                    @Override
                    public void accept(T t) {
                        super.accept(t);
                        if (callBack != null) {
                            callBack.accept(t);
                        }
                    }
                }, new HttpErrorConsumer() {
                    @Override
                    public void httpErrorMessage(String errorMsg, Throwable throwable) {
                        if (callBack != null) {
                            callBack.httpErrorMessage(errorMsg, throwable);
                        }
                    }
                });
    }

    public Disposable requestForJson(Observable<JsonObject> mObservable, @Nullable OnRequestCallBack<JsonObject> callBack) {
        return mObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpJsonConsumer() {
                    @Override
                    public void accept(JsonObject jsonObject) {
                        super.accept(jsonObject);
                        if (callBack != null) {
                            callBack.accept(jsonObject);
                        }
                    }
                }, new HttpErrorConsumer() {
                    @Override
                    public void httpErrorMessage(String errorMsg, Throwable throwable) {
                        if (callBack != null) {
                            callBack.httpErrorMessage(errorMsg, throwable);
                        }
                    }
                });
    }
}
