package com.wzly.base.retrofit.result;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/13
 *     desc   : 基本字段
 *     version: 1.0
 * </pre>
 */
public class BaseResult {

    @SerializedName("code")
    public int code;
    @SerializedName("msg")
    public String msg = "";

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
