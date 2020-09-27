package com.wzly.base.retrofit.constant;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/8/3
 *     desc   : 缓存策略常量
 *     version: 1.0
 * </pre>
 */
public class HttpCacheConstant {

    /**
     * 默认
     * 大众接口，有网 强行网络获取，无网 强行缓存获取
     */
    public final static int NET_AND_CACHE = 1;

    /**
     * 强行获取缓存， 没数据就没数据
     */
    public final static int FORCE_CACHE = 4;

    /**
     * 强行网络，没数据就没数据
     */
    public final static int FORCE_NETWORK = 5;

    /**
     * 有网缓存策略 ，有网 根据缓存有效期是否拿缓存，无网 强行缓存获取
     */
    public final static int TIME_LIMIT_NET_AND_CACHE = 2;

    /**
     * 强行获取缓存,并且有网的话再次用 NET_AND_CACHE 缓存策略请求数据
     * <p>
     * 使用场景 ：会请求两次， 第一次强行缓存获取，成功后再次从网络获取新的数据覆盖当前数据
     * <p>
     * 注意：
     * 强行从缓存获取没有数据的回调（目前测试是这样的） ： response.isSuccessful() is false ; code = 504 ； RxAndroid 回调 HttpException ， code == 504
     * 第二次请求时将该值改变为 {@link HttpCacheConstant#NET_AND_CACHE} , 否者会出现无限循环请求数据
     * （体验问题）可能还要在当前界面网络失败回调写一个逻辑，如果当前界面有数据，界面不做任何处理或者只提示网络异常； 不然会出现有数据之后界面又直接显示无数据
     */
    public final static int FORCE_CACHE_AND_REQUEST_AGAIN = 3;
}
