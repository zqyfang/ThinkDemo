package com.njtjs.thinktest.test;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by zqyfa on 2017/6/27.
 */

public class TjsWebView extends WebView {
    public TjsWebView(Context context) {
        super(context);
    }

    public TjsWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TjsWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 防止webview滑动和scrollview滑动冲突
     * 但是实际测试，此回调用不写也有效，也不会有滑动冲突
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
