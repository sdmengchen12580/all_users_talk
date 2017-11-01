package org.faqrobot.text.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * Created by iyunwen on 2016/9/12.
 */

public class MyWebView extends WebView {
    private boolean isEnd;

    public MyWebView(Context context) {
        super(context);
        initSettings();
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSettings();
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSettings();
    }

    private void initSettings() {
        WebSettings settings = this.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setDefaultTextEncodingName("UTF-8");
        //// TODO: 2016/11/16 网页更改
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setDomStorageEnabled(true);// 设置可以使用localStorage
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先使用缓存
        settings.setAppCacheMaxSize(10 * 1024 * 1024);// 缓存最多可以有10M
        settings.setAllowFileAccess(true);// 可以读取文件缓存(manifest生效)
        settings.setPluginState(WebSettings.PluginState.ON);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (MotionEvent.ACTION_DOWN == action) {
            return true;
        }
        if (MotionEvent.ACTION_MOVE == action) {
            return true;
        }
        if (MotionEvent.ACTION_UP == action) {
            return false;
        }
        return super.onTouchEvent(event);
    }


}
