package org.faqrobot.text.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 孟晨 on 2017/10/10.
 */

public  class Util_Log_Toast {

    /**log*/
    public static void log_e(Context context,String log_txt){
        Log.e("--------------------"+context+":",log_txt);
    }
    public static void log_e(String log_txt){
        Log.e("--------------------",log_txt);
    }


    /**击中对应的错误——吐司错误*/
    public static void hitErrorMsg(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
