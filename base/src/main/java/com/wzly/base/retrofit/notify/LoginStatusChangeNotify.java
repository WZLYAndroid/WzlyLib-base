package com.wzly.base.retrofit.notify;


import com.wzly.base.retrofit.HttpCodeCheck;

/**
 * <pre>
 *     @author : Jary
 *     e-mail :  jarylan@foxmail.com
 *     time   : 2020/3/24
 *     desc   : 登录状态: 登录失效时发的通知实体 : 暂无地方接受， 因为直接跳转登录页了， 没有其他逻辑
 *     version: 1.0
 * </pre>
 */
public class LoginStatusChangeNotify {

    /**
     * 对应接口返回的 code {@link HttpCodeCheck}
     */
    public int loginCode = -1;

    public LoginStatusChangeNotify(int loginCode) {
        this.loginCode = loginCode;
    }
}
