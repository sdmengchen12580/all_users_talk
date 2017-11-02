package org.faqrobot.text.ui.mactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechUnderstander;
import com.unisound.client.SpeechUnderstanderListener;
import org.faqrobot.text.R;
import org.faqrobot.text.constant.Config;
import java.util.ArrayList;
import java.util.List;

public class NewImageChangeActivity extends AppCompatActivity {

    /**************************************对象*************************************************
     * todo 1.图片滑动的时候会多滑动一张   2.图片用setimagerecouce或者bitmap会有问题
     * 1.控件
     * 2.手势监听
     * 3.图片资源
     * 问题：图片用setimagerecouce或者bitmap会有问题
     * 解决网址：http://www.cnblogs.com/wanqieddy/archive/2011/11/25/2263381.
     * 4.图片的集合
     * 5.语音唤醒对象
     * 6.存储屏幕的宽高对象
     * 7.语音唤醒的关键词集合
     * 8.人臉檢測的surfaceview
     * */
    private ViewFlipper mViewFlipper;
    private GestureDetector gestureDetector;
    private CustomGestureDetector customGestureDetector;
    int[] resources = {
            R.drawable.imgone,
            R.drawable.imgtwo,
            R.drawable.imgthree,
    };
    private ImageView[] imageView = new ImageView[3];
    private SpeechUnderstander mUnderstander;
    int screenWidth;
    int screenHeigh;
    List<String> list_wakeup_words;
    private static final String WAKEUP_TAG = "wakeup";

    /**************************************oncreat******************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image_change);
        initView();
        initGesture();
        initWakeUp();
    }

    /**初始化语音唤醒*/
    private void initWakeUp()
    {
        mUnderstander = new SpeechUnderstander(this, Config.appKey, null);
        mUnderstander.setOption(SpeechConstants.ASR_SERVICE_MODE, SpeechConstants.ASR_SERVICE_MODE_LOCAL);
//        mUnderstander.setOnlineWakeupWord(list_wakeup_words);
        mUnderstander.setListener(new SpeechUnderstanderListener() {
            @Override
            public void onResult(int type, String jsonResult) {
                Log.e("onResult: ","唤醒成功" );
            }
            @Override
            public void onEvent(int type, int timeMs) {
                switch (type) {
                    case SpeechConstants.WAKEUP_EVENT_RECOGNITION_SUCCESS:
                        Log.e("onResult: ","唤醒成功" );
                        startActivity(new Intent(NewImageChangeActivity.this,TabActivity.class));
                        /**喚醒成功后，釋放資源*/
                        mUnderstander.cancel();
                        mUnderstander.release(SpeechConstants.ASR_RELEASE_ENGINE, "");
                        mUnderstander = null;
                        finish();
                        break;
                    case SpeechConstants.ASR_EVENT_RECORDING_START:
                        Log.e("onResult: ","语音唤醒已开始");
                        break;
                    case SpeechConstants.ASR_EVENT_RECORDING_STOP:
                        Log.e("onResult: ","语音唤醒录音已停止");
                        break;
                    case SpeechConstants.ASR_EVENT_ENGINE_INIT_DONE:
                        Log.e("onResult: ","引擎初始化完成");
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onError(int type, String errorMSG) {
                toastMessage("errorMSG对应的值 = "+type);
                toastMessage("errorMSG = "+errorMSG);
                toastMessage("语音唤醒服务异常  异常信息：" + errorMSG);
            }
        });
        mUnderstander.init("");
        mUnderstander.start(WAKEUP_TAG);
    }

    /**吐司的工具类*/
    private void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**初始化手势监听*/
    private void initGesture()
    {
        /**获取屏幕的宽高*/
        attain_screen_width_or_height();
        /**初始化手势监听*/
        customGestureDetector = new CustomGestureDetector();
        gestureDetector = new GestureDetector(this, customGestureDetector);
    }

    /**初始化控件*/
    private void initView()
    {
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        for (int i = 0; i < resources.length; i++) {
            imageView[i]= new ImageView(this);
            imageView[i].setImageResource(resources[i]);
            imageView[i].setScaleType(ImageView.ScaleType.FIT_XY);
            mViewFlipper.addView(imageView[i]);
        }
        /**5s自动切换*/
//        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(5000);
        /**设置关键词*/
        list_wakeup_words = new ArrayList<>();
        list_wakeup_words.add("你好");
    }

    /**手势监听*/
    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float minMove = 120; // 最小滑动距离
            float minVelocity = 0; // 最小滑动速度
            float beginX = e1.getX();
            float endX = e2.getX();
            float beginY = e1.getY();
            float endY = e2.getY();
            /**左滑*/
            if (beginX - endX > minMove && Math.abs(velocityX) > minVelocity) {
                mViewFlipper.setInAnimation(getApplicationContext(), R.anim.flipper_right_in);
                mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.flipper_left_out);
                mViewFlipper.showNext();
                mViewFlipper.startFlipping();
            }
            /**右滑*/
            else if (endX - beginX > minMove && Math.abs(velocityX) > minVelocity) {
                mViewFlipper.setInAnimation(getApplicationContext(), R.anim.flipper_left_in);
                mViewFlipper.setOutAnimation(getApplicationContext(), R.anim.flipper_right_out);
                mViewFlipper.showPrevious();
                mViewFlipper.startFlipping();
            }
            /**大于屏幕的五分之四才上滑OK*/
            else if (beginY - endY > minMove && Math.abs(velocityY) > minVelocity && beginY > screenHeigh * 3 / 5) {
                startActivity(new Intent(getApplicationContext(), TabActivity.class));
                overridePendingTransition(R.anim.enter_chat, R.anim.leave_chat);
                finish();
            }
            /**下滑*/
            else if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity) {
//                Toast.makeText(getApplicationContext(), "下边", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

    /**获取屏幕宽高*/
    public void attain_screen_width_or_height()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
    }

    /**将手势监听传给onTouchEvent*/
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**销毁对象*/
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        imageView = null;
        resources = null;
        mViewFlipper = null;
        customGestureDetector = null;
        gestureDetector= null;
    }
}
