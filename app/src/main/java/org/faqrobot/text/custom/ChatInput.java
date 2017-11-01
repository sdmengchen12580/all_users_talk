package org.faqrobot.text.custom;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.faqrobot.text.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HouShuai on 2016/8/31.
 */

public class ChatInput extends RelativeLayout implements TextWatcher, View.OnClickListener {
    @BindView(R.id.btn_voice)
    ImageButton mbtnVoice;
    @BindView(R.id.btn_keyboard)
    ImageButton mbtnKeyboard;
    @BindView(R.id.voice_panel)
    TextView mvoicePanel;
    @BindView(R.id.btn_send)
    ImageButton mbtnSend;
    @BindView(R.id.input)
    EditText meditText;
    private boolean isSendVisible, isHoldVoiceBtn,
            isSendClick;
    private ChatView mChatView;
    private InputMode inputMode = InputMode.NONE;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    private Context mContext;
    private AnimationSet animationSet;

    public ChatInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context.getApplicationContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.custom_activity_chatinput, this);
        ButterKnife.bind(inflate);
        initListener();
        initView();
    }

    private void initListener() {
        mbtnSend.setOnClickListener(this);
        mbtnVoice.setOnClickListener(this);
        mbtnKeyboard.setOnClickListener(this);
        meditText.addTextChangedListener(this);
        mvoicePanel.setOnClickListener(this);
    }

    public enum InputMode {
        TEXT,
        VOICE,
        EMOTICON,
        MORE,
        NONE,
    }

    //对视图的操作
    private void initView() {
//        mbtnSend.setVisibility(GONE);
//        meditText.setVisibility(GONE);
//        mbtnKeyboard.setVisibility(VISIBLE);
//        mtextPanel.setVisibility(GONE);
//        mvoicePanel.setVisibility(VISIBLE);
//        mbtnVoice.setVisibility(GONE);
        //设置发送按钮
        setSendBtn();
        //设置声音和文本切换
        chooseVoiceOrText();
        meditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (hasFocus()) {
                    updateView(InputMode.TEXT);
                }
//                if (b){
//                }

            }
        });
        meditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mChatView.isFocus();
            }
        });
    }

    private void leavingCurrentState() {
        switch (inputMode) {
            case TEXT:
                View view = ((Activity) getContext()).getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                meditText.clearFocus();
                mbtnSend.setVisibility(GONE);
                startButtonAnimation();
                break;
            case VOICE:
                mvoicePanel.setVisibility(GONE);
                mbtnVoice.setVisibility(VISIBLE);
//                mbtnKeyboard.setVisibility(GONE);
                mbtnSend.setVisibility(VISIBLE);
                mvoicePanel_Animation_Cancel();
                break;
        }
    }

    private void updateView(InputMode mode) {
        if (mode == inputMode) return;
        leavingCurrentState();
        switch (inputMode = mode) {
            case TEXT:
                if (meditText.requestFocus()) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(meditText, InputMethodManager.SHOW_IMPLICIT);
                }
                mvoicePanel.setVisibility(GONE);
                mbtnVoice.setVisibility(VISIBLE);
//                mbtnKeyboard.setVisibility(GONE);
                mbtnSend.setVisibility(VISIBLE);
                meditText.setVisibility(VISIBLE);
                mChatView.stopListener();
                mvoicePanel_Animation_Cancel();
                break;
            case VOICE:
                mvoicePanel.setVisibility(VISIBLE);
//                mbtnVoice.setVisibility(GONE);
                meditText.setVisibility(GONE);
                mbtnKeyboard.setVisibility(VISIBLE);
                mbtnSend.setVisibility(GONE);
                mChatView.beginListener();
                break;
        }
    }

    private void chooseVoiceOrText() {
        mvoicePanel.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isHoldVoiceBtn = true;
                        updateVoiceView();
                        break;
                    case MotionEvent.ACTION_UP:
                        isHoldVoiceBtn = false;
                        updateVoiceView();
                        break;
                }
                return true;
            }
        });
    }

    private void updateVoiceView() {
        if (isHoldVoiceBtn) {
            mvoicePanel.setBackgroundResource(R.drawable.btn_voice_pressed);
        } else {
            mvoicePanel.setBackgroundResource(R.drawable.btn_voice_normal);
        }
    }

    private void setSendBtn() {
        if (isSendVisible) {
            mbtnSend.setBackgroundResource(R.drawable.send);
        } else {
            mbtnSend.setBackgroundResource(R.drawable.send_empty);
        }
    }

    /*
    * 观察text的改变
    * */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mChatView.textChang();
    }

    /*
     * 观察text的改变
     * */
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mChatView.textChang();
        isSendVisible = charSequence != null && charSequence.length() > 0;
        setSendBtn();
        mbtnSend.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isSendClick = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isSendClick = false;
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 关联聊天界面逻辑
     */
    public void setChatView(ChatView chatView) {
        this.mChatView = chatView;
    }

    /*
     * 观察text的改变
     * */
    @Override
    public void afterTextChanged(Editable s) {
        mChatView.textChang();

    }

    /*
     * 点选时间
     * */
    @Override
    public void onClick(View v) {
        Activity activity = (Activity) getContext();
        int id = v.getId();
        if (id == R.id.btn_send) {
            mChatView.sendText();
        }
        if (id == R.id.btn_voice) {
            if (activity != null && requestAudio(activity)) {
                updateView(InputMode.VOICE);
            }
        }
        if (id == R.id.btn_keyboard) {
            updateView(InputMode.TEXT);
        }

    }

    private boolean requestStorage(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestAudio(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestCamera(Activity activity) {
        if (afterM()) {
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean afterM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }


    private void mvoicePanel_Animation_Cancel() {
        if (null != animationSet) {
            animationSet.cancel();
            animationSet.reset();
            mvoicePanel.clearAnimation();
        }
    }

    /**
     * 获取输入框文字
     */
    public Editable getText() {
        return meditText.getText();
    }

    /**
     * 设置输入框文字
     */
    public void setText(String text) {
        meditText.setText(text);
    }

    /**
     * 设置输入框文字为空
     */
    public void setTextEmpty() {
        meditText.setText("");
    }

    /*
    * 开启底部控件动画
    * */
    public void startButtonAnimation() {
        animationSet = (AnimationSet) AnimationUtils.loadAnimation(mContext, R.anim.chatinput_mvoicepancel_animation);
        mvoicePanel.setText(R.string.chatinput_start_listener);
        mvoicePanel.setTextColor(Color.argb(255, 255, 255, 0));
        mvoicePanel.startAnimation(animationSet);
        mvoicePanel.setBackgroundResource(R.drawable.custom_chatinput_listener);
    }

    /*
     * 关闭底部动画控件
     * */
    public void stopButtonAnimation() {
        if (null != animationSet) {
            animationSet.cancel();
            animationSet.reset();
            mvoicePanel.clearAnimation();
            mvoicePanel.setText(mContext.getString(R.string.chat_press_talk));
            mvoicePanel.setTextColor(Color.argb(255, 255, 255, 255));
            mvoicePanel.setBackgroundResource(R.drawable.custom_chatinput_empty);

        }
    }

    public void setVoiceChang(int type) {
        mvoicePanel.setText(mContext.getString(R.string.chatinput_start_listener) + "-您的音量:" + type);
        mvoicePanel.setTextColor(Color.argb(255, 255, 255, 0));
        mvoicePanel.setBackgroundResource(R.drawable.custom_chatinput_listener);
    }

    public void setStarVoice() {
        mvoicePanel.setText(R.string.init_listener);
        mvoicePanel.setTextColor(Color.argb(255, 255, 255, 255));
        mvoicePanel.setBackgroundResource(R.drawable.custom_chatinput_init);
    }
}
