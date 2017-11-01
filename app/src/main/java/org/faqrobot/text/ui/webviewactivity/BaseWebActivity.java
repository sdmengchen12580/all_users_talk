package org.faqrobot.text.ui.webviewactivity;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by iyunwen on 2016/9/6.
 */

public abstract class BaseWebActivity extends Activity {
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**自身实现抽象方法*/
        setContentView(setLayout());
        bind = ButterKnife.bind(this);
        initView();
    }

    /**抽象方法——自定义布局和初始化数据*/
    protected abstract void initView();
    protected abstract int setLayout();
    /**解绑*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }


}
