package com.wzly.base.retrofit.consumer;

import com.hjq.base.R;
import com.wzly.base.retrofit.constant.BaseHttpCodeConstant;
import com.wzly.base.retrofit.exception.ErrorCodeException;
import com.wzly.base.retrofit.exception.LoginException;
import com.wzly.base.retrofit.listener.HttpErrorMessageListener;
import com.wzly.base.retrofit.notify.LoginStatusChangeNotify;
import com.wzly.base.utils.AppUtil;
import com.wzly.base.utils.NetworkUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.functions.Consumer;

/**
 * <pre>
 *     @author : Jary
 *     e-mail :  jarylan@foxmail.com
 *     time   : 2019/9/24
 *     desc   : 网络请求失败，统一处理各种情况
 *     version: 1.0
 * </pre>
 */
public abstract class HttpErrorConsumer implements Consumer<Throwable>, HttpErrorMessageListener {

    /**
     * 接口失败的两种情况：
     * 1. 接口真实失败
     * 2. 接口访问成功，返回数据 code != {@link BaseHttpCodeConstant#CODE_SUCCESS}
     * <p>
     * 1. 第一次获取缓存失败，需要再次获取数据判断
     * if (e instanceof HttpException
     * && ((HttpException) e).code() == 504
     * && "Unsatisfiable Request (only-if-cached)".equals(((HttpException) e).message())) {
     * //"失败后发起二次请求"
     * return;
     * }
     */
    @Override
    public void accept(Throwable e) {
        Logger.d("--------- 若网络错误日志不详细，请在日志窗口去掉过滤条件，搜索下面这句话查看具体错误 ：  " + e.getMessage());
        e.printStackTrace();

        //接口请求异常 统一处理； 包含自定义登录异常
        if (e instanceof LoginException && ((LoginException) e).isTokenExpried()) {
            //token 失效, 发送通知
            EventBus.getDefault().post(new LoginStatusChangeNotify(((LoginException) e).getErrorCode()));
            return;
        }

        if (e instanceof ErrorCodeException) {
            //code != {@link HttpCodeConstant#CODE_SUCCESS} 的情况
            httpErrorMessage(e.getMessage(), e);
            return;
        }

        if (!NetworkUtil.isConnected()) {
            //无网络
            httpErrorMessage(AppUtil.getContext().getString(R.string.tip_network_unavailable), e);
            return;
        }

        if (e.getMessage() != null) {

            if (e.getMessage().contains("after 10000ms") || e.getMessage().contains("timeout")) {
                //超时
                httpErrorMessage(AppUtil.getContext().getString(R.string.tip_network_timeout), e);
                return;
            }

            if (e.getMessage().contains("IllegalStateException") || e.getMessage().contains("JSON")) {
                //java.lang.IllegalStateException: Expected BEGIN_OBJECT but was BOOLEAN at line 1 column 32 path $.data
                //Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path $
                //接口返回 Json 格式与协定好的不一致， 是否要统计  接口类型
                httpErrorMessage(AppUtil.getContext().getString(R.string.tip_server_result_error), e);
                return;
            }

        }

        httpErrorMessage(AppUtil.getContext().getString(R.string.tip_request_error), e);
    }

}
