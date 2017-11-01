package org.faqrobot.text.ui.webviewactivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.faqrobot.text.mbroadcast.NetworkConnectChangedReceiver;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by iyunwen on 2016/9/6.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder bind;
    private NetworkConnectChangedReceiver mNetReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isOpenThisScreen();
        super.onCreate(savedInstanceState);
        //开启网络的监控
        openNetMonitor();
        bind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        unregisterReceiver(mNetReceiver);
    }


    private void openNetMonitor() {
        mNetReceiver = new NetworkConnectChangedReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(mNetReceiver, filter);
    }

    /**
     * 是否开启当前Activity
     */
    protected abstract void isOpenThisScreen();



}
