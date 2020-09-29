package com.wzly.base.retrofit;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/14
 *     desc   : 网络接口返回的 code 校验 ： code 与默认定义的不同， 请在主工程调用 {@link this#initCode(int, int...)}
 *     version: 1.0
 * </pre>
 */
public class HttpCodeCheck {

    /**
     * 请求成功 : 默认值
     */
    private final static int CODE_SUCCESS = 0;

    /**
     * token 失效 : 默认值
     */
    private final static int CODE_ERROR_INVALID_TOKEN = 100100;

    private static HttpCodeCheck mInstance;

    /**
     * 请求成功 CODE : 可由主工程重新赋值
     */
    private int codeSuccess = CODE_SUCCESS;

    /**
     * token 异常需要重新登录的 code list : 可由主工程重新赋值
     */
    private List<Integer> tokenInvalidCodeList = new ArrayList<>();

    private HttpCodeCheck() {
        tokenInvalidCodeList.add(CODE_ERROR_INVALID_TOKEN);
    }

    public static HttpCodeCheck getInstance() {
        if (mInstance == null) {
            synchronized (HttpCodeCheck.class) {
                if (mInstance == null) {
                    mInstance = new HttpCodeCheck();
                }
            }
        }
        return mInstance;
    }

    public void initCode(int codeSuccess, int... tokenInvalidCode) {
        this.codeSuccess = codeSuccess;
        if (tokenInvalidCode.length > 0) {
            tokenInvalidCodeList.clear();
            for (int i : tokenInvalidCode) {
                tokenInvalidCodeList.add(i);
            }
        }
    }

    /**
     * @return true 为 错误码
     */
    public boolean isErrorCode(int code) {
        return codeSuccess != code;
    }

    /**
     * 返回该 code 是否是无效 token code
     */
    public boolean isInvalidToken(int code) {
        return tokenInvalidCodeList.contains(code);
    }
}
