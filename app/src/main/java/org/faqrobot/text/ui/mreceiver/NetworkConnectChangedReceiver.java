package org.faqrobot.text.ui.mreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.faqrobot.text.entity.NetState;

import java.lang.ref.WeakReference;

/**
 * Created by 孟晨 on 2017/10/25.
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
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.isConnected()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        // connected to wifi
                        onReNetDialog("[ WIFI ]");
                        NetState.getInstant().setWifiAble(true);
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // connected to the mobile provider's data plan
                        NetState.getInstant().setMobleNetble(true);
                        onReNetDialog("[ MOBILE ]");
                    }
                } else {
                    Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                    onNetDialog();
                }
            } else {   // not connected to the internet
                Log.e(TAG, "当前没有网络连接，请确保你已经打开网络 ");
                NetState.getInstant().setMobleNetble(false);
                NetState.getInstant().setWifiAble(false);
                onNetDialog();
            }
        }
    }

    private void onNetDialog() {
        isFirst = true;
        Log.e("onNetDialog: ","检测到当前无网络接入，请确保网络通畅后再次尝试" );

    }

    private void onReNetDialog(String insertType) {
        if (!isFirst) return;
        isFirst = false;
        Log.e("onNetDialog: ","网络恢复" );
    }

}


