package com.wzly.base.cache;

import android.content.Context;
import android.os.Environment;

import com.wzly.base.utils.FileSizeUtil;
import com.wzly.base.utils.FileUtils;

/**
 * @author : Jary
 * e-mail :  jarylan@foxmail.com
 * date   : 2020/9/2 16:49
 * desc   : 应用数据缓存管理器
 * version: 1.0
 *
 * Environment.getDataDirectory() = /data
 * Environment.getDownloadCacheDirectory() = /cache
 * Environment.getExternalStorageDirectory() = /mnt/sdcard
 * Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
 * Environment.getRootDirectory() = /system
 * getPackageCodePath() = /data/app/com.my.app-1.apk
 * getPackageResourcePath() = /data/app/com.my.app-1.apk
 * getCacheDir() = /data/data/com.my.app/cache
 * getDatabasePath(“test”) = /data/data/com.my.app/databases/test
 * getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test
 * getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
 * getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
 * getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files
 * getFilesDir() = /data/data/com.my.app/files
 */
public class CacheCleanManager {

    /**
     * 获取缓存路径路径、 存放本应用产生的一些文件
     *
     * 当SD卡存在或者SD卡不可被移除的时候，就调用getExternalCacheDir()方法来获取缓存路径，否则就调用getCacheDir()方法来获取缓存路径。
     * 前者获取到的就是 /sdcard/Android/data//cache 这个路径，而后者获取到的是 /data/data//cache 这个路径。
     * 注意：这两种方式的缓存都会在卸载app的时候被系统清理到，而开发者自己在sd卡上建立的缓存文件夹，是不会跟随着app的卸载而被清除掉的。
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 获取缓存大小 (包括内外部缓存)
     * 最小单位为 MB
     */
    public static String getCacheSize(Context context) {
        try {
            Long cacheSize;
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                cacheSize = FileSizeUtil.getFileSizes(context.getExternalCacheDir())
                        + FileSizeUtil.getFileSizes(context.getCacheDir());
            } else {
                cacheSize = FileSizeUtil.getFileSizes(context.getCacheDir());
            }
            return FileSizeUtil.FormetFileSizeSpecifyMin(cacheSize, FileSizeUtil.SIZE_TYPE_MB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0.0MB";
    }

    /**
     * 清除 内外部缓存
     */
    public static void cleanCache(Context context) {
        cleanInternalCache(context);
        cleanExternalCache(context);
    }

    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     */
    private static void cleanInternalCache(Context context) {
        FileUtils.deleteDir(context.getCacheDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     */
    private static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            FileUtils.deleteDir(context.getExternalCacheDir());
        }
    }
}
