package org.faqrobot.text.initttsandwakeup;

import android.content.Context;
import android.util.Log;

import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechSynthesizerListener;
import com.unisound.client.SpeechUnderstander;
import com.unisound.client.SpeechUnderstanderListener;

import org.faqrobot.text.constant.Config;
import org.faqrobot.text.utils.Util_Log_Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static org.faqrobot.text.MyApplication.getContext;

/**
 * Created by 孟晨 on 2017/10/30.
 */

public class InitTts {
    /**
     * 单例
     */
    private static Context context;
    private static InitTts initTts = null;

    public static synchronized InitTts getInstance(Context context) {
        if (initTts == null) {
            initTts = new InitTts(context);
        }
        return initTts;
    }

    /**
     * 计时
     */
    private Timer timer_twenty_s;
    private int current_number = 15;

    public void setCurrent_number(int current_number) {
        this.current_number = current_number;
    }

    /*** 语音识别——语音合成*/
    public SpeechUnderstander mUnderstander;
    public SpeechSynthesizer mTTSPlayer;

    /**
     * 识别的结果
     */
    private StringBuffer mAsrResultBuffer;

    /**
     * 对应的采样率和说话的语种
     */
    private static int arraySample[] = new int[]{SpeechConstants.ASR_SAMPLING_RATE_BANDWIDTH_AUTO,
            SpeechConstants.ASR_SAMPLING_RATE_16K, SpeechConstants.ASR_SAMPLING_RATE_8K};
    private static String arrayLanguageStr[] = new String[]{SpeechConstants.LANGUAGE_MANDARIN,
            SpeechConstants.LANGUAGE_ENGLISH, SpeechConstants.LANGUAGE_CANTONESE};

    /**
     * 对fragment提供的接口
     */
    public interface Interface_give_fragment_to_use {
        /**
         * 文字显示右边
         */
        void inter_showTxtRight(String str);

        /**
         * 问题请求后台网络
         */
        void inter_getRobatQstion(String str);

        /**
         * 设置当前状态
         */
        void inter_current_status(int status);

        /**
         * 音量改变
         */
        void inter_volume_change(int volume);

        /**
         * 删除textview的内容
         */
        void inter_delect_textview();

        /**
         * text显示识别的内容
         */
        void inter_textview_show_recognize_word(String str);

        /**
         * 语音识别结束
         */
        void inter_speck_end();

        /**
         * 初始化合成说hello
         */
        void inter_init_speck_to_speck_hello();
    }

    /**
     * 接口成员变量——对外提供设置和清空接口的方法
     */
    public Interface_give_fragment_to_use minterface_give_fragment_to_use = null;

    public void set_interface(Interface_give_fragment_to_use interface_give_fragment_to_use) {
        Log.e("————————set_interface: ", "设置了接口");
        if (minterface_give_fragment_to_use != null) {
            minterface_give_fragment_to_use = null;
            this.minterface_give_fragment_to_use = interface_give_fragment_to_use;
        } else {
            this.minterface_give_fragment_to_use = interface_give_fragment_to_use;
        }
    }

    public void release_interface() {
        minterface_give_fragment_to_use = null;
    }


    /**
     * 没选择在构造方法中直接初始化语音识别和播报
     */
    private InitTts(Context context)
    {
        this.context = context;
        mAsrResultBuffer = new StringBuffer();
        /**初始化计时器*/
        initTimer();
    }

    /**
     * 初始化语音识别
     */
    public void initTts()
    {
        initRecognizer();
        initSpecker();
    }

    /**
     * 初始化计时器
     */
    private void initTimer()
    {
        timer_twenty_s = new Timer();
        timer_twenty_s.schedule(new TimerTask() {
            @Override
            public void run() {
                current_number -= 1;
                Util_Log_Toast.log_e(getContext(), "" + current_number);
                if (current_number == 0) {
                    Util_Log_Toast.log_e(getContext(), "计时结束");
                    timer_twenty_s.cancel();
                    // TODO: 2017/10/25 调到下个activity
//                    /**调到下个界面*/
//                    startActivity(new Intent(BaseChatActivity.this, TextActivity.class));
//                    finish();
                }
            }
        }, 0, 1000);
    }

    /**
     * 初始化语音识别
     */
    private void initRecognizer()
    {
        /**创建语音识别对象，appKey和 secret通过 http://dev.hivoice.cn/ 网站申请*/
        mUnderstander = new SpeechUnderstander(context, Config.appKey, Config.secret);
        /**开启可变结果*/
        mUnderstander.setOption(SpeechConstants.ASR_OPT_TEMP_RESULT_ENABLE, true);
        /**设置语义场景*/
        mUnderstander.setOption(SpeechConstants.NLU_SCENARIO, "videoDefault");
        /**在收到 onRecognizerStart 回调前，录音设备没有打开，请添加界面等待提示*/
        /**修改识别语音*/
        mUnderstander.setOption(SpeechConstants.ASR_SAMPLING_RATE, arraySample[1]);
        mUnderstander.setOption(SpeechConstants.ASR_LANGUAGE, arrayLanguageStr[0]);

        // TODO: 2017/9/28  保存录音数据——数据保存到哪里？
        // TODO: 2017/9/28   recognizer.setRecordingDataEnable(true);
        mUnderstander.setListener(new SpeechUnderstanderListener() {
            @Override
            public void onResult(int type, String jsonResult) {
                switch (type) {
                    /**在线识别*/
                    case SpeechConstants.ASR_RESULT_NET:
                        // TODO: 2017/10/26  在线识别结果，通常onResult接口多次返回结果，保留识别结果组成完整的识别内容。
                        Util_Log_Toast.log_e("调用在线识别");
                        if (jsonResult.contains("net_asr")
                                && jsonResult.contains("net_nlu")) {
                            try {
                                JSONObject json = new JSONObject(jsonResult);
                                JSONArray jsonArray = json.getJSONArray("net_asr");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String status = jsonObject.getString("result_type");
                                Util_Log_Toast.log_e("jsonObject = " + jsonObject.toString());
                                if (status.equals("full")) {
                                    String result = (String) jsonObject
                                            .get("recognition_result");
                                    /**jsonResult有结果，就能解析出识别到用户说的话-并且长度大于1*/
                                    if (jsonResult != null && result.length() > 2) {
                                        /**用户交互了，重新计时*/
                                        current_number = 15;
                                        Util_Log_Toast.log_e(getContext(), "用户说话为：" + result.toString());
                                        // TODO: 2017/10/26  右边显示用户说的话
                                        minterface_give_fragment_to_use.inter_showTxtRight(result.toString().trim());
                                        // TODO: 2017/10/26   请求nlp
                                        minterface_give_fragment_to_use.inter_getRobatQstion(result.toString().trim());
                                        // TODO: 2017/10/26  当前正在播报
                                        minterface_give_fragment_to_use.inter_current_status(Config.NUMNER_FOUR);
                                    } else if (result.length() == 2) {
                                        Util_Log_Toast.log_e(getContext(), "用户说一个字，不识别");
                                        // TODO: 2017/10/26  当前空闲状态
                                        minterface_give_fragment_to_use.inter_current_status(Config.NUMNER_ONE);
                                    } else if (result.isEmpty()) {
                                        Util_Log_Toast.log_e(getContext(), "用户没说话");
                                        // TODO: 2017/10/26  当前空闲状态
                                        minterface_give_fragment_to_use.inter_current_status(Config.NUMNER_ONE);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //取出语音识别结果
                            asrResultOperate(jsonResult);
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onEvent(int type, int timeMs) {
                switch (type) {
                    /**1.用户可以说话了*/
                    case SpeechConstants.ASR_EVENT_RECORDING_START:
                        Util_Log_Toast.log_e(getContext(), "录音设备打开，开始识别，用户可以开始说话");
                        // TODO: 2017/10/26 当前打开录音设备
                        minterface_give_fragment_to_use.inter_current_status(Config.NUMNER_TWO);
                        break;
                    /**2.说话开始*/
                    case SpeechConstants.ASR_EVENT_SPEECH_DETECTED:
                        Util_Log_Toast.log_e(getContext(), "用户开始说话");
                        break;
                    /**3.音量的改变*/
                    case SpeechConstants.ASR_EVENT_VOLUMECHANGE:
                        // 说话音量实时返回
                        int volume = (Integer) mUnderstander.getOption(SpeechConstants.GENERAL_UPDATE_VOLUME);
                        // TODO: 2017/10/26 音量大于多少的时候，重新计时
                        if (volume > 35) {
                            current_number = 15;
                        }
                        // TODO: 2017/10/26 音量改变
                        minterface_give_fragment_to_use.inter_volume_change(volume);
                        break;
                    /**4.识别完*/
                    case SpeechConstants.ASR_EVENT_NET_END:
                        Util_Log_Toast.log_e(getContext(), "识别完成");
                        break;
                    /**5.超时未说话*/
                    case SpeechConstants.ASR_EVENT_VAD_TIMEOUT:
                        Util_Log_Toast.log_e(getContext(), "长时间不说话，但是不语音识别");
//                            mUnderstander.stop();
                        break;
                    /**6.录音停止*/
                    case SpeechConstants.ASR_EVENT_RECORDING_STOP:
                        Util_Log_Toast.log_e(getContext(), "录音停止，即将进入解析用户言语");
                        // TODO: 2017/10/26 当前正在识别解析用户说的话
                        minterface_give_fragment_to_use.inter_current_status(Config.NUMNER_THREE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(int type, String errorMSG) {
                if (errorMSG != null) {
                    /**显示错误信息*/
                    Util_Log_Toast.hitErrorMsg(getContext(), errorMSG);
                }
            }
        });
        mUnderstander.init("");
    }

    /**
     * 语音解析工具方法
     */
    private void asrResultOperate(String jsonResult)
    {
        JSONObject asrJson;
        try {
            asrJson = new JSONObject(jsonResult);
            JSONArray asrJsonArray = asrJson.getJSONArray("net_asr");
            JSONObject asrJsonObject = asrJsonArray.getJSONObject(0);
            String asrJsonStatus = asrJsonObject.getString("result_type");
            // TODO: 2017/10/26 清空显示当前说的话的textview-后期可以删除
            minterface_give_fragment_to_use.inter_delect_textview();
//            if (asrJsonStatus.equals("change")) {
//                mRecognizerResultText.append(mAsrResultBuffer.toString());
//                mRecognizerResultText.append(asrJsonObject.getString("recognition_result"));
//            } else {
//                mAsrResultBuffer.append(asrJsonObject.getString("recognition_result"));
//                mRecognizerResultText.append(mAsrResultBuffer.toString());
//            }
            if (!asrJsonStatus.equals("change")) {
                mAsrResultBuffer.append(asrJsonObject.getString("recognition_result"));
                // TODO: 2017/10/26 textview设置当前文字为解析的结果文字
                // mRecognizerResultText.append(mAsrResultBuffer.toString());
                minterface_give_fragment_to_use.inter_textview_show_recognize_word(mAsrResultBuffer.toString());
                Util_Log_Toast.log_e(getContext(), mAsrResultBuffer.toString() + "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化语音合成
     */
    public void initSpecker()
    {
        /**创建语音合成（合成就是播报）对象*/
        mTTSPlayer = new SpeechSynthesizer(context, Config.appKey, Config.secret);
        mTTSPlayer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_NET);
        /**设置语音合成回调监听*/
        mTTSPlayer.setTTSListener(new SpeechSynthesizerListener() {
            @Override
            public void onEvent(int type) {
                switch (type) {
                    case SpeechConstants.TTS_EVENT_INIT:
                        // 初始化成功回调
                        break;
                    case SpeechConstants.TTS_EVENT_SYNTHESIZER_START:
                        // 开始合成回调
                        break;
                    case SpeechConstants.TTS_EVENT_SYNTHESIZER_END:
                        // 合成结束回调
                        break;
                    case SpeechConstants.TTS_EVENT_BUFFER_BEGIN:
                        // 开始缓存回调
                        break;
                    case SpeechConstants.TTS_EVENT_BUFFER_READY:
                        // 缓存完毕回调
                        break;
                    case SpeechConstants.TTS_EVENT_PLAYING_START:
                        // 开始播放回调
                        break;
                    case SpeechConstants.TTS_EVENT_PLAYING_END:
                        Log.e("—————语音识别播报的类———————", "播报完成");
                        if (minterface_give_fragment_to_use != null) {
                            minterface_give_fragment_to_use.inter_speck_end();
                        }
                        // 播放完成回调
                        break;
                    case SpeechConstants.TTS_EVENT_PAUSE:
                        // 暂停回调
                        break;
                    case SpeechConstants.TTS_EVENT_RESUME:
                        // 恢复回调
                        break;
                    case SpeechConstants.TTS_EVENT_STOP:
                        // 停止回调
                        break;
                    case SpeechConstants.TTS_EVENT_RELEASE:
                        // 释放资源回调
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(int type, String errorMSG) {
                /**吐司出错*/
                Util_Log_Toast.hitErrorMsg(getContext(), errorMSG);
            }
        });
        mTTSPlayer.init("");
        /**首次播报*/
        mTTSPlayer.playText("您好客官，请问有什么可以帮您的吗？");
        minterface_give_fragment_to_use.inter_current_status(Config.NUMNER_FOUR);
    }

    /**
     * 释放资源
     */
    public void release_value() {
        /**计时未结束时候切屏——计时结束后直接释放*/
        if (current_number != 0) {
            timer_twenty_s.cancel();
            timer_twenty_s = null;
        } else {
            timer_twenty_s = null;
        }
        if (mUnderstander != null) {
//            mUnderstander.stop();
            mUnderstander = null;
        }
        if (mTTSPlayer != null) {
//            mTTSPlayer.stop();
            mTTSPlayer = null;
        }
        /**清空3变量*/
        mAsrResultBuffer = null;
        initTts = null;
        context = null;
    }
}
