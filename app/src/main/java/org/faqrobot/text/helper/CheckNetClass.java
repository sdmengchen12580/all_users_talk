package org.faqrobot.text.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.faqrobot.text.MyApplication;
import org.faqrobot.text.baserxjava.Base_Http_Retrofit;
import org.faqrobot.text.constant.Config;
import org.faqrobot.text.entity.Access_Token;
import org.faqrobot.text.entity.KeeyRobatOnlion;
import org.faqrobot.text.utils.Util_Log_Toast;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.faqrobot.text.MyApplication.getContext;

/**
 * Created by 孟晨 on 2017/10/30.
 */

public class CheckNetClass {

    private static Context mcontext;
    public static CheckNetClass getInstanc(Context context){
        if (checkNetClass==null){
            checkNetClass = new CheckNetClass(context);
        }
        return checkNetClass;
    }
    private static CheckNetClass checkNetClass=null;


    /**
     * 是否有网
     */
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    public Subscription access_token_retrofit;
    public Subscription robarOnlion_retrofit;

    private CheckNetClass(Context context){
        mcontext = context;
        /**检测网络*/
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        mcontext.registerReceiver(networkChangeReceiver, intentFilter);
    }

    /**
     * 监听网络广播
     */
    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Util_Log_Toast.log_e(mcontext, "当前有网");
                /**设置全局变量有网*/
                ((MyApplication) mcontext.getApplicationContext()).setHas_net(true);
                /**获取accesstoken*/
                getAccess_Token(Config.APPID, Config.SECRET);
            } else {
                Util_Log_Toast.log_e(mcontext, "当前无网");
                /**设置全局变量无网*/
                ((MyApplication) mcontext.getApplicationContext()).setHas_net(false);
            }
        }
    }

    /**
     * 获取ACCESS_TOKEN网络请求封装
     */
    String maccess_token;
    private void getAccess_Token(String appId, String secret) {
        access_token_retrofit = Base_Http_Retrofit.getInstance()
                .geterver()
                .getAccess_Token(appId, secret)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Access_Token>() {
                    @Override
                    public void onCompleted() {
                        Util_Log_Toast.log_e(mcontext, "获取accesstoken值完成，准备设置机器人在线");
                        setRobatOnlioning();
                    }
                    @Override
                    public void onError(Throwable e) {
                        Util_Log_Toast.log_e(mcontext, "获取accesstoken值失败");
                    }
                    @Override
                    public void onNext(Access_Token access_token) {
                        if (!access_token.getAccess_token().isEmpty()) {
                            maccess_token = access_token.getAccess_token();
                            Util_Log_Toast.log_e(getContext(), "获取accesstoken值为：" + maccess_token);
                            ((MyApplication) mcontext.getApplicationContext()).setAccess_token(maccess_token);
                        }
                    }
                });
    }

    /**
     * 请求机器人长连接网络请求封装
     */
    private void setRobatOnlioning() {
        robarOnlion_retrofit = Base_Http_Retrofit.getInstance()
                .geterver()
                .setRobotOnlioning(maccess_token, Config.ROBAT_ONLIONING, "", Config.CLIENDID, Config.SOURCEID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<KeeyRobatOnlion>() {
                    @Override
                    public void onCompleted() {
                        Util_Log_Toast.log_e(getContext(), "机器人在线请求成功");
                        maccess_token = null;
                        Log.e("onCompleted: ","注销广播" );
                        mcontext.unregisterReceiver(networkChangeReceiver);
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onNext(KeeyRobatOnlion keeyRobatOnlion) {
                    }
                });
    }


    /**
     * 销毁（广播和rxjava）
     */
    public void release_api_and_broadcase(){
        if (null != access_token_retrofit) {
            access_token_retrofit.unsubscribe();
            access_token_retrofit = null;
        }
        if (null != robarOnlion_retrofit) {
            robarOnlion_retrofit.unsubscribe();
            robarOnlion_retrofit = null;
        }
    }
}
