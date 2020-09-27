package com.wzly.base.retrofit.consumer;


import com.wzly.base.retrofit.constant.BaseHttpCodeConstant;
import com.wzly.base.retrofit.exception.ErrorCodeException;
import com.wzly.base.retrofit.exception.LoginException;
import com.wzly.base.retrofit.result.BaseResult;

import io.reactivex.functions.Consumer;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/14
 *     desc   : 统一处理实体返会 code ; token 失效, code != {@link BaseHttpCodeConstant#CODE_SUCCESS}
 *              注 ： 子类不可删除此句代码 super.accept(Object);
 *     version: 1.0
 * </pre>
 */
public abstract class HttpBeanConsumer<T extends BaseResult> implements Consumer<T> {

    @Override
    public void accept(T t) {
        if (t.code == BaseHttpCodeConstant.CODE_ERROR_INVALID_TOKEN) {
            throw new LoginException(t.msg, t.code);
        }
        if (t.code != BaseHttpCodeConstant.CODE_SUCCESS) {
            throw new ErrorCodeException(t.msg, t, t.code);
        }
    }

}
