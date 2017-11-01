package org.faqrobot.text.initttsandwakeup;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechUnderstander;
import com.unisound.client.SpeechUnderstanderListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 孟晨 on 2017/11/1.
 */

public class InitWakeUp {

    private static InitWakeUp initWakeUp=null;

    public static InitWakeUp getInitWakeUp(Context context)
    {
        if(initWakeUp==null){
            initWakeUp = new InitWakeUp(context);
        }
        return initWakeUp;
    }

    /**单例*/
    private InitWakeUp(Context context)
    {
        mcontext = context;
        /**设置关键词*/
        list_wakeup_words = new ArrayList<>();
        list_wakeup_words.add("你好");
        initWakeUp();
    }

    /**初始化语音唤醒*/
    private SpeechUnderstander mUnderstander;
    List<String> list_wakeup_words;
    private Context mcontext;
    private void initWakeUp()
    {
        mUnderstander = new SpeechUnderstander(mcontext,"4qqq4qrxw6mvccxttl7fffuhh23atwubogdvcmyg", null);
        mUnderstander.setOption(SpeechConstants.ASR_SERVICE_MODE, SpeechConstants.ASR_SERVICE_MODE_LOCAL);
        mUnderstander.setOnlineWakeupWord(list_wakeup_words);
        mUnderstander.setListener(new SpeechUnderstanderListener() {
            @Override
            public void onEvent(int i, int i1) {
                switch (i) {
                    case SpeechConstants.WAKEUP_EVENT_RECOGNITION_SUCCESS:
                        toastMessage("唤醒成功");
                        break;
                    case SpeechConstants.ASR_EVENT_RECORDING_START:
                        Log.e( "onEvent: ","语音唤醒已开始" );
                        break;
                    case SpeechConstants.ASR_EVENT_RECORDING_STOP:
                        Log.e( "onEvent: ","语音唤醒已停止" );
                        break;
                    case SpeechConstants.ASR_EVENT_ENGINE_INIT_DONE:
                        toastMessage("引擎初始化完成，即将开始人脸唤醒");
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onError(int i, String s) {
                toastMessage("语音唤醒服务异常  异常信息：" + s);
            }
            @Override
            public void onResult(int i, String s) {
            }
        });
        mUnderstander.init("");
    }


    /**吐司的工具类*/
    private void toastMessage(String message) {
        Toast.makeText(mcontext, message, Toast.LENGTH_SHORT).show();
    }
}
