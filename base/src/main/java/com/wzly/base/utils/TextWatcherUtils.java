package com.wzly.base.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * <pre>
 *     author : Jimmy.tsang
 *     e-mail : jimmytsangfly@gmail.com
 *     time   : 2017/03/27
 *     desc   :  输入框文字监听
 *     version: 1.0
 * </pre>
 */

public class TextWatcherUtils implements TextWatcher {

    public TextWatcherUtils() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public  <T> void removeTextWatchListener(T t) {
        if (t instanceof TextView) {
            ((TextView) t).removeTextChangedListener(this);
        }
    }
}
