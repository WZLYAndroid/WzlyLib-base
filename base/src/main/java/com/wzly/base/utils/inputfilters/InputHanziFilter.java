package com.wzly.base.utils.inputfilters;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : Jary
 *     e-mail : jarylan@foxmail.com
 *     time   : 2020/3/8
 *     desc   : 汉字过滤
 *     version: 1.0
 * </pre>
 */
public class InputHanziFilter implements InputFilter {

    private Pattern hanzi = Pattern.compile("[\u4e00-\u9fa5]",
            Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher hanziMatcher = hanzi.matcher(source);
        if (hanziMatcher.find()) {
            return "";
        }
        return null;
    }

}
