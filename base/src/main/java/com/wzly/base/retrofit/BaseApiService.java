package com.wzly.base.retrofit;


import com.google.gson.JsonObject;
import com.wzly.base.retrofit.result.BaseResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * <pre>
 *     @author : jarylan
 *     e-mail : jarylan@foxmail.com
 *     time   : 2018/12/13
 *     desc   : 接口定义
 *            注意 ： @FormUrlEncoded @POST 请求必须至少一个参数  @Field (String 允许为 null)
 *     version: 1.0
 * </pre>
 */
public interface BaseApiService {

    /**
     * 通用提交图片 POST 请求,上传图片或者文件直接调用封装好的 map 传进来即可
     */
    @Multipart
    @POST
    Observable<JsonObject> requestPostImage(@Url String url, @PartMap Map<String, RequestBody> map);

    /**
     * 通用 POST 请求 , 参数注解为 @FieldMap
     */
    @FormUrlEncoded
    @POST
    Observable<JsonObject> requestPostForJsonObject(@Url String url, @FieldMap Map<String, String> map);

    /**
     * 通用 GET 请求 ， 参数注解为 @QueryMap
     */
    @GET
    Observable<JsonObject> requestGetForJsonObject(@Url String url, @QueryMap Map<String, String> map);

    /**
     * 下载文件很大则使用 @Streaming 这个注解
     * 解析文件： body.contentLength()文件大小    body.byteStream(); 文件流
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    /**
     * 通用返回 BaseResult , 适用于只要成功失败结果的接口
     */
    @GET
    Observable<BaseResult> requestGetForBaseResult(@Url String url, @QueryMap Map<String, String> map);

    /**
     * 通用返回 BaseResult , 适用于只要成功失败结果的接口
     */
    @FormUrlEncoded
    @POST
    Observable<BaseResult> requestPostForBaseResult(@Url String url, @FieldMap Map<String, String> map);
}
