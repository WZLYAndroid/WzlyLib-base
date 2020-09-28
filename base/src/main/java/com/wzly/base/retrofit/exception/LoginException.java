package com.wzly.base.retrofit.exception;

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

    public int getErrorCode() {
        return mErrorCode;
    }
}
