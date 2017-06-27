package com.njtjs.thinktest;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.njtjs.thinktest.view.TjsGradationScrollView;
import com.njtjs.thinktest.view.StatusBarUtil;

public class ToolBarActivity extends AppCompatActivity  implements TjsGradationScrollView.ScrollViewListener{
    private TjsGradationScrollView scrollView;

//    private ListView listView;

    private TextView textView;
    private int height;
    private ImageView ivBanner;
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tool_bar);
        StatusBarUtil.setImgTransparent(this);
        wv= (WebView) findViewById(R.id.wv);
        scrollView = (TjsGradationScrollView) findViewById(R.id.scrollview);
//        listView = (ListView) findViewById(R.id.listview);
        textView = (TextView) findViewById(R.id.textview);
        ivBanner = (ImageView) findViewById(R.id.iv_banner);

        ivBanner.setFocusable(true);
        ivBanner.setFocusableInTouchMode(true);
        ivBanner.requestFocus();

        initListeners();
//        initData();
        init();
    }


    private void init(){
        wv.loadUrl("http://m.hizhu.com/");
        WebSettings ws=wv.getSettings();
        ws.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {

        ViewTreeObserver vto = ivBanner.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                //如果不减去顶部textview的高度则下面listview的条目都滑动顶部textview下面了，顶部背景色还没有完全不透明
                height = ivBanner.getHeight()-textView.getHeight();
                scrollView.setScrollViewListener(ToolBarActivity.this);
            }
        });
    }



//    private void initData() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ToolBarActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.data));
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ToolBarActivity.this, ""+position, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    @Override
    public void onScrollChanged(TjsGradationScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (oldy-y<=0) {//这是向上滑动
            if (y <= 0) {   //设置标题的背景颜色
                textView.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
            } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                float scale = (float) y / height;
                float alpha = (255 * scale);
                textView.setTextColor(Color.argb((int) alpha, 255, 255, 255));
//                textView.setBackgroundColor(Color.argb((int) alpha, 144,151,166));
                textView.getBackground().setAlpha((int) alpha);//这样之设置透明度，才是符合的，如果按照上一句，会变化的很突兀
                //此处是给imageview绘制了滤镜，此处使用的 PorterDuff.Mode.SRC_OVER参数是正常绘制显示，上下层绘制叠盖的意思
                ivBanner.getDrawable().setColorFilter(Color.argb((int) alpha, 144, 151, 166), PorterDuff.Mode.SRC_OVER);
            } else {    //滑动到banner下面设置普通颜色
                textView.setBackgroundColor(Color.argb( 255, 144, 151, 166));
            }
        }else {//向下滑动
            if (y <= 0) {
                textView.setBackgroundColor(Color.argb( 0, 144, 151, 166));
            } else if (y > 0 && y <= height) {
                float scale = (float) y / height;
                float alpha = (255 * scale);
                textView.setTextColor(Color.argb((int) alpha, 255, 255, 255));
                textView.getBackground().setAlpha((int)height/y);
                ivBanner.getDrawable().setColorFilter(Color.argb((int) alpha, 144, 151, 166), PorterDuff.Mode.SRC_OVER);
            } else {
                textView.setBackgroundColor(Color.argb( 255, 144, 151, 166));
            }
        }
    }
}
