package com.wzly.base.retrofit.interceptor;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/14
 *     desc   : 日志拦截器
 *     version: 1.0
 * </pre>
 */
public class LoggingInterceptor implements Interceptor {

    private final Charset UTF8 = StandardCharsets.UTF_8;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();

        String body = null;

        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (charset != null) {
                body = buffer.readString(charset);
            }
            body = unicodeToUTF_8(body);
        }

//        Logger.w("发送请求\nmethod：%s\nurl：%s\nheaders: %sbody：%s",
//                request.method(), request.url(), request.headers(), body);

        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        String rBody = null;

        BufferedSource source = null;
        if (responseBody != null) {
            source = responseBody.source();
        }
        if (source != null) {
            // Buffer the entire body.
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }
            if (charset != null) {
                rBody = buffer.clone().readString(charset);
            }
            rBody = unicodeToUTF_8(rBody);
        }

        String str = String.format("收到响应 : code = %s message = %s time = %sms%n请求url：%s%n请求headers: %s%n请求body：%s%n响应headers: " +
                "%s%n响应body：%s", response.code(), response.message(), tookMs, response.request().url(), request.headers(), body, response.headers(), rBody);
        Logger.w(str);
//        if (AppUtils.isAppDebug(MyApplication.getAppContext())) {
//           //假如是 debug 版本，为了方便测试， 将 log 写入文件并保存
//            String temp = TimeUtils.getNowTimeString() +"\n"+ str + "\n\n";
//            FileUtils.writeFileFromString(App.getAppContext().getExternalCacheDir() + "/network/retrofit_log.json",temp,true);
//            //如果返回的格式为非 json ，是后台将错误信息一起返回了， 另外起一个文件来存储（看作是后台错误日志） ， 给后台人员看，让他们自己去解决
            try {
                new JSONObject(rBody);
            } catch (JSONException e) {
                e.printStackTrace();
                //非法 json ，把当前用户 id 也保存一下
//                String temp2 = TimeUtils.getNowTimeString() + "\n" + "当前登录的用户 id ：" + SettingPrefences.getIntance().getIDValue() +"\n" + str + "\n\n";
//                FileUtils.writeFileFromString(App.getAppContext().getExternalCacheDir() + "/network/retrofit_log_illegality_json.json",temp2,true);
//                Toast.makeText(MyApplication.getAppContext(), "后台返回 json 格式的问题 ： " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
//        }

        return response;
    }


    /**
     * unicode格式转为UTF-8格式
     *
     * @param src src
     * @return src
     */
    private static String unicodeToUTF_8(String src) {
        if (null == src) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < src.length(); ) {
            char c = src.charAt(i);
            if (i + 6 < src.length() && c == '\\' && src.charAt(i + 1) == 'u') {
                String hex = src.substring(i + 2, i + 6);
                try {
                    out.append((char) Integer.parseInt(hex, 16));
                } catch (NumberFormatException nfe) {
                    nfe.fillInStackTrace();
                }
                i = i + 6;
            } else {
                out.append(src.charAt(i));
                ++i;
            }
        }
        return out.toString();

    }

}
