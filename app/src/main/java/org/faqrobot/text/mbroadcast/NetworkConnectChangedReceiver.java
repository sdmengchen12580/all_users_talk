package org.faqrobot.text.mbroadcast;

/**
 * Created by housh on 2017/4/28.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.faqrobot.text.entity.NetState;

import java.lang.ref.WeakReference;

/**
 * 网络改变监控广播
 * 监听网络的改变状态,只有在用户操作网络连接开关(wifi,mobile)的时候接受广播,
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkConnectChangedRe";
    private WeakReference<Activity> mActivity;
    private boolean isFirst;

    public NetworkConnectChangedReceiver() {
        super();
    }

    public NetworkConnectChangedReceiver(Activity activity) {
        this.mActivity = new WeakReference<>(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.isConnected()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        Toast.makeText(context, "当前使用wifi。", Toast.LENGTH_SHORT).show();
                        NetState.getInstant().setWifiAble(true);
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        NetState.getInstant().setMobleNetble(true);
                        Toast.makeText(context, "当前使用移动数据。", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "当前没有网络连接，请确保你已经打开网络。", Toast.LENGTH_SHORT).show();
                }
            } else {
                NetState.getInstant().setMobleNetble(false);
                NetState.getInstant().setWifiAble(false);
                Toast.makeText(context, "当前没有网络连接，请确保你已经打开网络。", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


