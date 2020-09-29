package com.wzly.base.retrofit.consumer;


import com.wzly.base.retrofit.HttpCodeCheck;
import com.wzly.base.retrofit.exception.ErrorCodeException;
import com.wzly.base.retrofit.exception.LoginException;
import com.wzly.base.retrofit.result.BaseResult;

import io.reactivex.functions.Function;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/17
 *     desc   : 统一处理实体返回 code ： token 失效, code 错误}
 *              注 ： 子类不可删除此句代码 super.accept(Object);
 *     version: 1.0
 * </pre>
 */
public abstract class HttpBeanFunction<T extends BaseResult, R> implements Function<T, R> {

    @Override
    public R apply(T t) {
        if (HttpCodeCheck.getInstance().isInvalidToken(t.code)) {
            throw new LoginException(t.msg, t.code);
        }
        if (HttpCodeCheck.getInstance().isErrorCode(t.code)) {
            throw new ErrorCodeException(t.msg, t, t.code);
        }
        return null;
    }
}
