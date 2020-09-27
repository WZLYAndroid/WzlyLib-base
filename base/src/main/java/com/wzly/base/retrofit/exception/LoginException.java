package com.wzly.base.retrofit.exception;


import com.wzly.base.retrofit.constant.BaseHttpCodeConstant;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/17
 *     desc   : 自定义登录异常
 *     version: 1.0
 * </pre>
 */
public class LoginException extends RuntimeException {

    private int mErrorCode;

    public LoginException(String message, int mErrorCode) {
        super(message);
        this.mErrorCode = mErrorCode;
    }

    /**
     * token 是否失效
     *
     * @return 失效返回 true
     */
    public boolean isTokenExpried() {
        return mErrorCode == BaseHttpCodeConstant.CODE_ERROR_INVALID_TOKEN;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}
