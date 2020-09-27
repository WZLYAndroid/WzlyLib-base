package com.wzly.base.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author : Jary
 * e-mail :  jarylan@foxmail.com
 * date   : 2020/9/1 11:17
 * desc   : 数字工具类 ： 保留小数， 数字格式
 * version: 1.0
 */
public class NumberUtil {

    /**
     * 版本号转 int
     *
     * @param version 1.0.0  去掉 “.”
     */
    public static int versionNameToInt(String version) {
        if (TextUtils.isEmpty(version)) {
            return -1;
        }
        try {
            String str = version.replace(".", "");
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 数字分割格式 ； 三位一分割
     *
     * @param num 1000
     * @return "1,000"
     */
    @SuppressLint("DefaultLocale")
    public static String getDigitalDivide(int num) {
        return String.format("%,d", num);
    }

    /**
     * 保留两位小数 方式一 1
     * 不管传入的任何值，均保留两位小数
     * //待验证是否符合四色五入
     */
    public static String getDecimals2M1(int num) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(num);
    }

    /**
     * 保留两位小数 方式一 2
     * 保留小数点后面不为0的两位小数，这种写法不能保证保留2为小数，但能保证最后一位数不为0
     * //待验证是否符合四色五入
     */
    public static String getDecimals2M2(int num) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(num);
    }

    /**
     * 这种方法不管传入的值是多少，均保留两位小数，并且符合四舍五入的规则。
     */
    @SuppressLint("DefaultLocale")
    public static String getDecimals2M3(int num) {
        return String.format("%.2f", num);
    }

    /**
     * 使用这种写法若小数点后均为零，则保留一位小数，并且有四舍五入的规则。
     */
    public static String getDecimals2M4(int num) {
        BigDecimal bd = new BigDecimal(num);
        return String.valueOf(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 保留两位小数
     */
    public static float keepTwoPlaces(float number) {
        /*
         * BigDecimal.ROUND_CEILING   向上取   直接丢去入一位
         * BigDecimal.ROUND_FLOOR     向下取   直接丢去
         * BigDecimal.ROUND_HALF_UP   四舍五入
         * 2 表示保留两位小数
         */
        return new BigDecimal(number).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
} 
