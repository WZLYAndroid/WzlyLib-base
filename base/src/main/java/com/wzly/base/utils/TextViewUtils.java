package com.wzly.base.utils;

import android.widget.EditText;
import android.widget.TextView;

/**
 * <pre>
 *     author : Jimmy.tsang
 *     e-mail : jimmytsangfly@gmail.com
 *     time   : 2017/03/27
 *     desc   :  TextView and EditTextView 工具类
 *     version: 1.0
 * </pre>
 */
public final class TextViewUtils {

    /**
     * 获取EditText输入的内容
     *
     * @param editText EditText
     * @return 返回EditText输入的内容
     */
    public static String getEditTextString(EditText editText) {
        String str = "";
        if (null != editText) {
            str = editText.getText().toString().trim();
        }
        return str;
    }

    /**
     * 获取TextView 内容
     *
     * @param textView textView
     * @return 返回TextView 内容
     */
    public static String getTextViewString(TextView textView) {
        String str = "";
        if (null != textView) {
            str = textView.getText().toString().trim();
        }
        return str;
    }

    /**
     * 清理EditText 输入的内容
     *
     * @param editText editText
     */
    public static void clearEditTextContent(EditText editText) {
        if (null != editText) {
            editText.getText().clear();
        }
    }

    /**
     * 移动光标到最后位置，如果没有获取焦点，则先获取
     */
    public static void moveCursorLast(EditText editText) {
        if (null != editText) {
            editText.setSelection(editText.getText().length());
        }
    }
}
