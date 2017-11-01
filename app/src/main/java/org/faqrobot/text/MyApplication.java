package org.faqrobot.text;


import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
import org.faqrobot.text.initttsandwakeup.CheckNetClass;

/**
 * Created by 孟晨 on 2017/10/26.
 */

public class MyApplication extends Application {

    /**
     * 全局的context
     */
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        MyApplication.mContext = mContext;
    }

    /**
     * 全局的accesstoken值
     */
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * 是否有网
     */
    private boolean has_net = false;

    public boolean isHas_net() {
        return has_net;
    }

    public void setHas_net(boolean has_net) {
        this.has_net = has_net;
    }

    /**
     * 用户的名字-人脸检测实现
     */
    private String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    private CheckNetClass checkNetClass;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        /**检测内存泄露*/
        LeakCanary.install(this);
        /**检测网络*/
        checkNetClass= CheckNetClass.getInstanc(mContext);
    }
}
