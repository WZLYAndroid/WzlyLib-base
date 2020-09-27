package com.wzly.base.utils.inputfilters;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * <pre>
 *     author : Jary
 *     e-mail : jarylan@foxmail.com
 *     time   : 2020/3/8
 *     desc   : 过滤空格，回车
 *     version: 1.0
 * </pre>
 */
public class InputSpaceFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (source.equals(" ") || source.toString().contentEquals("\n")) {
            return "";
        } else {
            return null;
        }
    }
}
