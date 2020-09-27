package com.wzly.base.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;

/**
 * <pre>
 *   @author : wiggins
 *   Date: 2020/4/10
 *   Time: 11:15
 *   desc : 图片加载工具类
 * </pre>
 */
public class GlideUtils {

    /**
     * 默认加载图片； 默认占位图
     *
     * @param context   context
     * @param path      图片地址
     * @param imageView 需要显示的ImageView
     */
    public static void loadImageView(Context context, Object path, ImageView imageView) {
        GlideApp.with(context)
                .load(path)
                .into(imageView);
    }

    /**
     * 加载图片不需要默认占位图
     */
    public static void loadImageViewNoPlaceholder(Context context, Object path, ImageView imageView) {
        GlideApp.with(context)
                .load(path)
                .apply(new RequestOptions().placeholder(0).error(0))
                .into(imageView);
    }

}
