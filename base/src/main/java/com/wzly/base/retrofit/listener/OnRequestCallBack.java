package com.wzly.base.retrofit.listener;

/**
 * <pre>
 *     @author : Jary
 *     e-mail :  jarylan@foxmail.com
 *     time   : 2020/3/25
 *     desc   : 接口请求面向 View 的统一回调
 *     version: 1.0
 * </pre>
 */
public interface OnRequestCallBack<T> extends HttpErrorMessageListener {

    /**
     * 访问网络数据成功回调;  code 一定是成功 的情况
     * T 的类型 ：  (？extend BaseResult) 或者 JsonObject
     */
    void accept(T result);

}
