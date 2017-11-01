package org.faqrobot.text.ui.webviewactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.StyleRes;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.faqrobot.text.R;
import org.faqrobot.text.custom.SpringView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 网页展示
 * Created by iyunwen on 2016/9/6.
 */

public class WebActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.web_toolbar)
    Toolbar webToolbar;
    @BindView(R.id.finish)
    Button finish;
    @BindView(R.id.back)
    Button back;
    @BindView(R.id.forward)
    Button forward;
    @BindView(R.id.refresh)
    Button refresh;
    @BindView(R.id.stop)
    Button stop;
    @BindView(R.id.web_bottom_nagetive)
    RelativeLayout webBottomNagetive;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.spring_refresh)
    SpringView springRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_web);
        ButterKnife.bind(this);
        initView();
    }


    @Override
    public void setTheme(@StyleRes int resid) {
        super.setTheme(resid);
    }

    private static final int MIN_CLICK_DELAY_TIME = 10000;
    private long oldTime;
    private WebViewThread webviewThread;


    @SuppressLint("SetJavaScriptEnabled")
    protected void initView() {
        setSupportActionBar(webToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebview.loadUrl(url);
                return false;
            }
        });
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setBuiltInZoomControls(true);
        Intent intent = getIntent();
        String webUrl = intent.getStringExtra("webUrl");
        mWebview.loadUrl(webUrl);
        mWebview.setWebViewClient(new WebViewClient(){

        });
        setListener();
        oldTime = Calendar.getInstance().getTimeInMillis();
        //设置时间监听
        webviewThread = new WebViewThread();
        webviewThread.start();

        springRefresh.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mWebview.reload();
            }

            @Override
            public void onLoadmore() {

            }
        });
    }

    private void setListener() {
        refresh.setOnClickListener(this);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        stop.setOnClickListener(this);
        finish.setOnClickListener(this);
    }
/*监听软键盘的点击事件*/


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        for (int i = 0; i < 280; i++) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
           /*隐藏软键盘*/
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(WebActivity.this.getCurrentFocus().getWindowToken(), 0);
                    setOldTimeForTouch();
                    return true;
                }
            } else if (event.getKeyCode() == i) {
                setOldTimeForTouch();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStoping = true;
        setOldTimeForTouch();
    }

    private void setOldTimeForTouch() {
        oldTime = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebview.canGoBack()) {
                mWebview.goBack();//返回上一页面
                return true;
            } else {
                finish();//返回上一级
            }
        }
        return super.onKeyDown(keyCode, event);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
                mWebview.reload();
                break;
            case R.id.forward:
                mWebview.goForward();//返回上一页面
                break;
            case R.id.back:
                mWebview.goBack();//返回上一页面
                break;
            case R.id.stop:
                mWebview.stopLoading();
                break;
            case R.id.finish:
                finish();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldTime = Calendar.getInstance().getTimeInMillis();
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.dispatchTouchEvent(ev);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止说话
        isStoping = false;
        //销毁当前webview持有的对象
//        mWebview.removeAllViews();
//        if (null != mWebview) {
//            mWebview.clearHistory();
//            mWebview.pauseTimers();
//            mWebview.clearCache(true);
//            mWebview.clearFormData();
//            mWebview.destroy();
//
//        }
    }

    /**
     * 是否开启当前Activity
     */
    @Override
    protected void isOpenThisScreen() {

    }

    private boolean isStoping = true;

    class WebViewThread extends Thread {
        public void run() {
            super.run();
            synchronized (this) {
                while (isStoping) {
                    SystemClock.sleep(1000);
                    Message m = new Message();
                    m.what = 0x1;
                    mHandler.sendMessage(m);
                }
            }
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x1:
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    if (currentTime - oldTime > MIN_CLICK_DELAY_TIME) {
                        finish();
                    }
                    break;
            }
        }


    };
}
