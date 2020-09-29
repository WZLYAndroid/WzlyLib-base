package com.wzly.base.retrofit.consumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
 *     desc   : 统一处理 json 返回 code ; token 失效, code 错误
 *              注 ： 子类不可删除此句代码 super.accept(Object);
 *     version: 1.0
 * </pre>
 */
public abstract class HttpJsonFunction<R> implements Function<JsonObject, R> {

    @Override
    public R apply(JsonObject jsonObject) {
        BaseResult result = new Gson().fromJson(jsonObject, BaseResult.class);
        if (HttpCodeCheck.getInstance().isInvalidToken(result.code)) {
            throw new LoginException(result.msg, result.code);
        }
        if (HttpCodeCheck.getInstance().isErrorCode(result.code)) {
            throw new ErrorCodeException(result.msg, jsonObject, result.code);
        }
        return null;
    }
}
