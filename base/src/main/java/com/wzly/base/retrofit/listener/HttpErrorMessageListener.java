package com.wzly.base.retrofit.listener;


import com.wzly.base.retrofit.constant.HttpCodeCheck;
import com.wzly.base.retrofit.consumer.HttpErrorConsumer;
import com.wzly.base.retrofit.exception.ErrorCodeException;
import com.wzly.base.retrofit.exception.LoginException;

/**
 * <pre>
 *     @author : Jary
 *     e-mail :  jarylan@foxmail.com
 *     time   : 2020/3/24
 *     desc   : 接口请求，其他错误信息，转化成可直接提示的信息； 比如 ：无网络，数据解析异常，网络连接超时等
 *     version: 1.0
 * </pre>
 */
public interface HttpErrorMessageListener {

    /**
     * 接口失败的两种情况：
     * 1. 接口真实失败
     * 2. 接口访问成功，返回数据 ； 但 code 错误 {@link HttpCodeCheck#isSuccessCode(int)}
     *
     * @param errorMsg 错误信息、可直接吐丝提示； 详见{@link HttpErrorConsumer#accept(Throwable)} 的处理
     * @param e        原生错误、 包含自定义的 {@link ErrorCodeException},{@link LoginException}
     *                 注 ： 需要用 code 错误的数据
     *                 if(e instanceof ErrorCodeException && ((ErrorCodeException) e).getResponse() != null && ((ErrorCodeException) e).getResponse() instanceof JsonObject/BaseResult){
     *                 //do sth.
     *                 return;
     *                 }
     */
    void httpErrorMessage(String errorMsg, Throwable e);

}
