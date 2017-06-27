package com.njtjs.thinktest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class TjsNoScrollListview extends ListView {
  
    public TjsNoScrollListview(Context context) {
        super(context);
    }  
  
    public TjsNoScrollListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }  
  
    public TjsNoScrollListview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }  
  
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //这是为了防止scrollview和listview滑动冲突的
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}  