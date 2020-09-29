package com.wzly.base;

import android.app.Application;

import com.wzly.base.retrofit.HttpCodeCheck;
import com.wzly.base.utils.AppUtil;

/**
 * @author : Jary
 * e-mail :  jarylan@foxmail.com
 * date   : 2020/9/29 9:53
 * desc   : BaseLib 初始化管理
 * version: 1.0
 */
public class BaseLib {

    private BaseLib() {
    }

    public static void init(Application application, int codeSuccess, int... tokenInvalidCode) {
        AppUtil.init(application);
        HttpCodeCheck.getInstance().initCode(codeSuccess, tokenInvalidCode);
    }

} 
