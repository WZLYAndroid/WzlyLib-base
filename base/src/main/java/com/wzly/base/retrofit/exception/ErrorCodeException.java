package com.wzly.base.retrofit.exception;


import com.wzly.base.retrofit.constant.BaseHttpCodeConstant;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/18
 *     desc   : 自定义异常 ， 接口请求成功，但是 code != {@link BaseHttpCodeConstant#CODE_SUCCESS}
 *     version: 1.0
 * </pre>
 */
public class ErrorCodeException extends RuntimeException {

    /**
     * 接口返回的数据， 类型 ：  (？extend BaseResult) 或者 JsonObject
     */
    private Object t;
    private int mErrorCode;

    public ErrorCodeException(String message, Object t, int mErrorCode) {
        super(message);
        this.t = t;
        this.mErrorCode = mErrorCode;
    }

    public Object getResponse() {
        return t;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}
