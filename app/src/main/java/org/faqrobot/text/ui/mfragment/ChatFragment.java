package org.faqrobot.text.ui.mfragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.unisound.client.SpeechSynthesizer;
import com.unisound.client.SpeechUnderstander;

import org.faqrobot.text.MyApplication;
import org.faqrobot.text.R;
import org.faqrobot.text.adapter.MyRecycleViewAdapter;
import org.faqrobot.text.baserxjava.Http_Retroif;
import org.faqrobot.text.constant.Config;
import org.faqrobot.text.custom.ChatView;
import org.faqrobot.text.entity.GetRobatResult;
import org.faqrobot.text.entity.GusList;
import org.faqrobot.text.entity.MyQuestion;
import org.faqrobot.text.entity.RobotAnswer;
import org.faqrobot.text.initttsandwakeup.InitTts;
import org.faqrobot.text.ui.mactivity.NewImageChangeActivity;
import org.faqrobot.text.ui.mactivity.TabActivity;
import org.faqrobot.text.ui.mactivity.UpPersonInfoActivity;
import org.faqrobot.text.ui.rece_broad.MyMusicServer;
import org.faqrobot.text.ui.webviewactivity.WebActivity;
import org.faqrobot.text.utils.HtmlParser;
import org.faqrobot.text.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.keyHeight;


// TODO: 2017/10/26 http://www.jb51.net/article/112933.htm
// TODO: 2017/10/26 fragment中使用手势监听
public class ChatFragment extends Fragment implements ChatView, MyRecycleViewAdapter.OnRecycleViewAudioClickListenter,
        MyRecycleViewAdapter.OnRecycleViewImageClickListenter,
        MyRecycleViewAdapter.OnRecycleViewRichMediaClickListenter,
        MyRecycleViewAdapter.OnRecycleViewTextClickListenter,
        MyRecycleViewAdapter.OnRecycleViewisSpeakingClickListenter,
        MyRecycleViewAdapter.OnRecycleViewVideoClickListenter,
        View.OnLayoutChangeListener, View.OnClickListener {

    // TODO: 2017/11/2

    /**
     * 无用，有待删除
     */
    public ChatFragment() {
    }

    @Override
    public void sendText() {

    }

    @Override
    public void beginListener() {

    }

    @Override
    public void stopListener() {

    }

    @Override
    public void textChang() {

    }

    @Override
    public void isFocus() {

    }

    /********************************************手势监听*************************************************
     * 1.屏幕的宽高
     * 2.手勢監聽的對象
     */
    int screenWidth;
    int screenHeigh;
    private TabActivity.MyOnTouchListener onTouchListener;
    private GestureDetector mDetector;
    private CustomGestureDetector customGestureDetector;

    /**
     * 獲取屏幕的寬高
     */
    public void attain_screen_width_or_height() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
    }

    /**
     * fragment中的手势监听，切換頁面
     */
    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("———fragment———", "fragment中触屏重新计时");
            initTts.setCurrent_number(15);
            return super.onDown(e);
        }

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
            }
            /**右滑*/
            else if (endX - beginX > minMove && Math.abs(velocityX) > minVelocity) {
            }
            /**大于屏幕的五分之四才上滑OK*/
            else if (beginY - endY > minMove && Math.abs(velocityY) > minVelocity && beginY > screenHeigh * 4 / 5) {
                Log.e("chatfragment:", "上滑");
                startActivity(new Intent(getActivity(), UpPersonInfoActivity.class));
                /**销毁*/
                ((TabActivity) getActivity()).finish();
            }
            /**小于五分之一下滑才OK*/
            else if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity && beginY < screenHeigh * 1 / 6) {
                Log.e("chatfragment:", "下滑");
                startActivity(new Intent(getActivity(), NewImageChangeActivity.class));
                /**销毁*/
                ((TabActivity) getActivity()).finish();
            }
            return false;
        }
    }


    /**
     * 傳入手勢監聽
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        customGestureDetector = new CustomGestureDetector();
        mDetector = new GestureDetector(getActivity(), customGestureDetector);
        onTouchListener = ev -> {
            mDetector.onTouchEvent(ev);
            return false;
        };
        /**注册手势监听*/
        ((TabActivity) getActivity()).registerMyOnTouchListener(onTouchListener);
    }


    /*********************************************第三个界面传来播报的问题***************************************************
     * 对第三个fragment提供的播报对应问题的方法
     */
    public void speck_rel_quesstions(String string) {
        /**当前正在播报*/
        handler.sendEmptyMessage(Config.NUMNER_THREE);
        /**请求后台*/
        getRobatQstion(string);
    }


    /************************************************ 释放资源***************************************************************
     * 1.关闭2线程，停止播報和监听
     * 2.停止播报和聆听
     * 3.状态改为播报
     * 4.注销接口
     * 5.data清空
     * 6.注销播报识别对象
     * 7.注销手势监听
     */
    @Override
    public void onStop() {
        super.onStop();
        checking_is_idel = false;
        if (check_status_is_idel != null) {
            check_status_is_idel.interrupt();
            check_status_is_idel = null;
        }
        checking_is_speck = false;
        if (check_status_is_speck != null) {
            check_status_is_speck.interrupt();
            check_status_is_speck = null;
        }
        if (statue == Config.NUMNER_FOUR) {
            mTTSPlayer.stop();
        } else if (statue == Config.NUMNER_ONE || statue == Config.NUMNER_TWO || statue == Config.NUMNER_THREE) {
            mUnderstander.cancel();
        }
        statue = Config.NUMNER_FOUR;
        /**监听置空*/
        initTts.release_interface();
        /**注销tts*/
        initTts.release_value();
        initTts = null;
        minterface = null;
        if (null != data) {
            data.clear();
        }
        mRecognizerText = null;
        mAsrResultBuffer = null;
        mRecyclerView = null;
        ((TabActivity) getActivity()).unregisterMyOnTouchListener(onTouchListener);
        Log.e("onStop: ", "东西释放完全了");
    }


    /*****************************************聊天相关start**********************************************************/
    public SpeechUnderstander mUnderstander;
    public SpeechSynthesizer mTTSPlayer;
    RecyclerView mRecyclerView;
    public MyRecycleViewAdapter mAdapter;
    public static int currentMusic;
    public List<GetRobatResult> data = new ArrayList<>();
    public TextView txview_status;  //显示当前是否聆听播报
    public ProgressBar mVolume;   //音量
    private Button stopSpeck;   //停止说话

    //用户说的话
    @SuppressWarnings("unused")
    private String mRecognizerText = "";
    private StringBuffer mAsrResultBuffer;

    //1.空闲   2.识别中  3.解析  4.播报  5.死亡——不播报不识别
    //当前的状态为说话
    public int statue = Config.NUMNER_FOUR;

    //线程检测是否为空闲状态
    private Thread check_status_is_idel;
    public boolean checking_is_idel = true;
    //线程检测是否为播报状态
    private Thread check_status_is_speck;
    public boolean checking_is_speck = true;

    //改变textview的显示
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Config.NUMNER_ONE:
                   mVolume.setProgress(0);
                    break;
                case Config.NUMNER_TWO:
                    txview_status.setText("当前正在聆听");
                    break;
                case Config.NUMNER_THREE:
                    txview_status.setText("当前正在播报");
                    break;
                case Config.NUMNER_FOUR:
                    txview_status.setText("点击说话");
                    break;
                default:
                    break;
            }
        }
    };


    /*****************************************oncreat方法start**********************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_chat, container, false);
        attain_screen_width_or_height();
        initView(mview);
        /**初始化tts*/
        initTts();
        /**线程一直检测当前是否为空闲状态*/
        check_now_status_is_idel();
        /**线程一直检测当前是否为播报状态——重新计时*/
        check_now_status_is_speck();
        return mview;
    }


    /*********************************************初始化语音播报**************************************************************
     * 传入播报和识别的接口
     */
    private InitTts initTts;
    private InitTts.Interface_give_fragment_to_use minterface;

    private void initTts() {
        initTts = InitTts.getInstance(getActivity());
        /**接口传递到class中*/
        if (minterface == null) {
            Log.e("initTts: ", "接口为空");
            minterface = new InitTts.Interface_give_fragment_to_use() {
                @Override
                public void inter_showTxtRight_and_get_robotanswer(String str) {
                    statue = Config.NUMNER_FOUR;
                    showTxtRight(str);
                    getRobatQstion(str);
                }

                @Override
                public void inter_current_status(int status) {
                    statue = status;
                }

                @Override
                public void inter_volume_change(int volume) {
                    mVolume.setProgress(volume);
                }

                @Override
                public void inter_speck_end() {
                    // TODO: 2017/11/3 播报完为什么会是聆听状态
                    Log.e("inter_speck_end: ", "播报完成的回调，此时的status为：" + statue);
                    /**小图标的改变*/
                    speakingend();
                    /**播报完成后，由播报转换为识别*/
                    if (statue == Config.NUMNER_FOUR) {
                        Log.e("inter_speck_end: ", "播报切换到识别");
                        statue = Config.NUMNER_ONE;
                        initTts.setCurrent_number(15);
                    }
                    /**
                     * 此种状态适用于播报前，给当前的状态设置为死亡状态。就会播报完什么都不做
                     * 专门留给第三个fragment播报用的
                     */
                    else if (statue == Config.NUMNER_FIVE) {
                        Log.e("inter_speck_end: ", "当前死亡状态，不做操作");
                    }
                    else if(statue == Config.NUMNER_TWO||statue == Config.NUMNER_THREE){
                        Log.e("inter_speck_end: ", "播报完之后，应该为播报状态。错误，强制停止聆听状态，并重启聆听");
                        mUnderstander.cancel();
                        statue = Config.NUMNER_ONE;
                    }
                }
                    //                public void inter_init_speck_to_speck_hello() {
                    //        /**清空之前的数据,并更新适配器*/
                    //        data.clear();
                    //        mAdapter.notifyDataSetChanged();
                    //        GetRobatResult getRobatResult = new GetRobatResult();
                    //        getRobatResult.setDirect(Config.Direct.RECEIVE);
                    //        getRobatResult.setTime(TimeUtil.getChatTimeStr());
                    //        ArrayList<RobotAnswer> robotAnswers = new ArrayList<>();
                    //        RobotAnswer robotAnswer = new RobotAnswer();
                    //        robotAnswer.setMsgType(Config.Type.TXT);
                    //        robotAnswer.setAnsCon("客官，咱们又见面啦。");
                    //        robotAnswers.add(robotAnswer);
                    //        getRobatResult.setRobotAnswer(robotAnswers);
                    //        data.add(getRobatResult);
                    //        /**首次播报*/
                    //        mTTSPlayer.playText("客官，咱们又见面啦。");
                    //        /**当前正在播报*/
                    //        statue = Config.NUMNER_FOUR;
                    //        handler.sendEmptyMessage(Config.NUMNER_THREE);
                    //                }
            };
        }
        /**先传接口，在初始化播报和识别。就会播报后立马转识别*/
        initTts.set_interface(minterface);
        initTts.initTts();
        /**拿到语音对象*/
        this.mUnderstander = initTts.mUnderstander;
        this.mTTSPlayer = initTts.mTTSPlayer;
    }


    /**************************************************初始化控件*************************************************************
     * */
    private void initView(View view) {
        mAsrResultBuffer = new StringBuffer();
        /**初始化RecyclerView*/
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        mAdapter = new MyRecycleViewAdapter(getContext(), data);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        //初始化监听事件
        mAdapter.setOnRecycleViewAudioClickListenter(this);
        mAdapter.setOnRecycleViewImageClickListenter(this);
        mAdapter.setOnRecycleViewRichMediaClickListenter(this);
        mAdapter.setOnRecycleViewVideoClickListenter(this);
        mAdapter.setOnRecycleViewTextClickListenter(this);
        mAdapter.setOnRecycleViewisSpeakingClickListenter(this);
        /**非recyclerview相关*/
        stopSpeck = (Button) view.findViewById(R.id.stop_speck);
        mVolume = (ProgressBar) view.findViewById(R.id.volume_progressbar);
        txview_status = (TextView) view.findViewById(R.id.tv_speak);
        stopSpeck.setOnClickListener(this);
        txview_status.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_speak:
                /**聆听时候，点击没用*/
                if (statue == Config.NUMNER_FOUR) {
                    mTTSPlayer.stop();
                    statue = Config.NUMNER_ONE;
                }
                /**显示点击说话的时候，点击转为聆听状态*/
                else if (statue == Config.NUMNER_FIVE) {
                    statue = Config.NUMNER_ONE;
                }
                break;
            /**当前正在播报，停止播报*/
            case R.id.stop_speck:
                Log.e("onClick:点击关闭播报 ","当前的状态为"+statue);
                // TODO: 2017/11/3 播报完为什么是录音状态 
                if (statue == Config.NUMNER_FOUR||statue==Config.NUMNER_TWO) {
                    mTTSPlayer.stop();
                    statue = Config.NUMNER_ONE;
                }
                break;
        }
    }

    /**
     * 检测是否为空闲状态
     */
    private void check_now_status_is_idel() {
        Log.e("check_now_status_is_idel: ", "检测当前是否为空闲状态");
        check_status_is_idel = new Thread(() -> {
            while (checking_is_idel) {
                if (statue == Config.NUMNER_ONE) {
                    Log.e("————当前为空闲状态","切换成录音状态，并且直接打断播报" );
                    // TODO: 2017/11/3 播报完之后，直接打断播报
                    mUnderstander.cancel();
                    /**状态由空闲状态切换至录音状态*/
                    statue = Config.NUMNER_TWO;
                    /**清空stringbuffer和dittext*/
                    mAsrResultBuffer.delete(0, mAsrResultBuffer.length());
                    mVolume.setProgress(0);
                    /**重启聆听状态*/
                    mUnderstander.start();
                    /**当前正在识别*/
                    handler.sendEmptyMessage(Config.NUMNER_TWO);
                }
            }
        });
        check_status_is_idel.start();
    }

    /**
     * 线程一直检测当前是否为播报状态——重新计时
     */
    private void check_now_status_is_speck() {
        Log.e("check_now_status_is_idel: ", "检测当前是否为播报状态");
        check_status_is_speck = new Thread(() -> {
            while (checking_is_speck) {
                if (statue == Config.NUMNER_FOUR) {
                    initTts.setCurrent_number(15);
                }
            }
        });
        check_status_is_speck.start();
    }

    /**
     * 1.点击图片的回调
     * 2.点击音频的回调
     * 3.点击网页的回调
     * 4.....
     */
    @Override
    public void setImageClick(View view, int position) {
    }

    @Override
    public void setVideoClick(View view, int position) {
    }

    private void startIntent2Web(String path) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("webUrl", path);
        startActivity(intent);
    }

    @Override
    public void setRichMediaClick(View view, int position, int index) {
        startIntent2Web(data.get(position).getUrlRichLink().get(index));
    }

    @Override
    public void setTextClick(View view, int position, int index, int type) {
        try {
            switch (type) {
                case 0x1:
                    if (!data.get(position).getRobotAnswer().get(0).getGusList().isEmpty() && data.get(position).getRobotAnswer().get(0).getGusList().size() > 0) {
                        RobotAnswer robotAnswer = data.get(position).getRobotAnswer().get(0);
                        robotAnswer.setAnsCon(getString(R.string.suggest_word));
                        GusList gusList = robotAnswer.getGusList().get(index);
                        showTxtRight(gusList.getSeedQuestion().getQuestion());
                        getRobatQstion(gusList.getSeedQuestion().getQuestion());
                    }
                    break;
                case 0x2:
                    if (!data.get(position).getRobotAnswer().get(0).getRelateList().isEmpty() && data.get(position).getRobotAnswer().get(0).getRelateList().size() > 0) {
                        MyQuestion myQuestion = data.get(position).getRobotAnswer().get(0).getRelateList().get(index);
                        String rel = myQuestion.getRel();
                        if ("-1".equals(rel)) {
                            getRobatQstion(myQuestion.getQuestion().trim());
                            showTxtRight(myQuestion.getQuestion().trim());
                        } else {
                            getRobatRelQstion(rel, myQuestion.getQuestion().trim());
                            showTxtRight(myQuestion.getQuestion().trim());
                        }
                    }
                    break;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            Toast.makeText(getActivity(), "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            Toast.makeText(getActivity(), "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 改变右边的小图标
     */
    protected void speakingend() {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setPlaying(false);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setisSpeakingClick(ImageView imageview, int position, boolean isSpeaking) {
        if (isSpeaking) {
            for (int i = 0; i < data.size(); i++) {
                data.get(i).setPlaying(false);
            }
            mTTSPlayer.stop();
//            mTTSPlayer.init("");
            imageview.setImageResource(R.drawable.stopspeak);
            mAdapter.notifyDataSetChanged();
        } else {
            /**聆听时候，想播报前天已经播报过的，需要考虑这种情况*/
            if (statue == Config.NUMNER_ONE || statue == Config.NUMNER_TWO || statue == Config.NUMNER_THREE) {
                mUnderstander.cancel();
                mVolume.setProgress(0);
                handler.sendEmptyMessage(Config.NUMNER_THREE);
            }
            data.get(position).setPlaying(true);
            imageview.setImageResource(R.drawable.speaking);
            String ansCon = data.get(position).getRobotAnswer().get(0).getAnsCon();
            tranTxtToSpeak(ansCon);
            mAdapter.notifyDataSetChanged();
        }
    }

    //广播类。进行音频的广播
    class ProgressReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MyMusicServer.ACTION_UPDATE_PROGRESS.equals(action)) {
                int progress = intent.getIntExtra(MyMusicServer.ACTION_UPDATE_PROGRESS, 0);
                if (progress > 0) {
//                    currentPosition = progress / 1000;
                }
            } else if (MyMusicServer.ACTION_UPDATE_CURRENT_MUSIC.equals(action)) {
                currentMusic = intent.getIntExtra(MyMusicServer.ACTION_UPDATE_CURRENT_MUSIC, 0);
            } else if (MyMusicServer.ACTION_UPDATE_DURATION.equals(action)) {
                int Max = intent.getIntExtra(MyMusicServer.ACTION_UPDATE_DURATION, 0);
//                currentMax = Max;
            }
        }
    }

    //开启音频播放器
    @Override
    public void setAudioStart(View view, int position) {
//        long newTime = System.currentTimeMillis();
//        if ((newTime - lastTime) > 2000) {
//            lastTime = newTime;
//            Toast.makeText(this, "请勿重复点击", Toast.LENGTH_SHORT).show();
//        } else {
//        try {
//            GetRobatResult getRobatResult = data.get(position);
////            maudioThread = new audioThread(position, getRobatResult);
//            String urlVoice = getRobatResult.getUrlVoice();
//            if (oldPosition != position) {
//                //关闭非当前的音频图标
//                for (GetRobatResult result :
//                        data) {
//                    result.setPlaying(false);
//                }   //开启当前图标
//                getRobatResult.setPlaying(true);
//                if (musicSampleBinder.isPlaying()) {
//                    musicSampleBinder.stopPlay();
//                }
//                musicSampleBinder.startPlay(urlVoice, currentPosition);
//                getRobatResult.setTotalTime(currentMax);
//                mAdapter.notifyDataSetChanged();
//                isOnclick = true;
//                oldPosition = position;
//            } else {
//                if (!isOnclick) {
//                    //开启播放
//                    isOnclick = true;
//                    if (musicSampleBinder.isPlaying()) {
//                        musicSampleBinder.stopPlay();
//                    }
//                    musicSampleBinder.startPlay(urlVoice, currentPosition);
//                    getRobatResult.setPlaying(true);
//                    mAdapter.notifyDataSetChanged();
//                } else {
//                    //关闭播放
//                    if (musicSampleBinder.isPlaying()) {
//                        isOnclick = false;
//                        musicSampleBinder.stopPlay();
//                        getRobatResult.setPlaying(false);
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        } catch (Exception e) {
//        }
    }

    //设置音频播放器
    @Override
    public void setAudioProgress(View view, int position, int progress) {
//        musicSampleBinder.changeProgress(progress);
    }

    /*****************************************网络请求start**********************************************************/
    private Subscription questionRetrofit;
    private Subscription mRobatRelQstionsubscribe;

    public void getRobatQstion(String content) {
        String token=((MyApplication) getActivity().getApplicationContext()).getAccess_token();
        Log.e("新获取的accesstoken值为: ",  token+ "");
        if(token==null){
            Toast.makeText(getActivity(), "后台获取不到token值,或者网速太慢", Toast.LENGTH_SHORT).show();
            return;
        }
        questionRetrofit = Http_Retroif.getInstance().geterver()
                .getRobatResult(((MyApplication) getActivity().getApplicationContext()).getAccess_token(), Config.QUESTION, Config.SOURCEID, content, Config.CLIENDID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetRobatResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.i("AA", e.toString());
                    }

                    @Override
                    public void onNext(GetRobatResult getRobatResult) {
                        Log.i("AA", "当前的内容结果为：" + getRobatResult.toString());
                        setTypeAnswer(getRobatResult);
                    }
                });
    }

    public void getRobatRelQstion(String content, String question) {
        Log.e("新获取的accesstoken值为: ", ((MyApplication) getActivity().getApplicationContext()).getAccess_token() + "");
        mRobatRelQstionsubscribe = Http_Retroif.getInstance().geterver()
                .getStreamRLResult(((MyApplication) getActivity().getApplicationContext()).getAccess_token(), Config.ROBAT_GWTFLOW, Integer.valueOf(content), question, Config.CLIENDID, Config.SOURCEID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetRobatResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(GetRobatResult getRobatResult) {
                        setTypeAnswer(getRobatResult);
                    }
                });
    }

    private void setTypeAnswer(GetRobatResult getRobatResult) {
        //将question中请求的结果转化为语音
        RobotAnswer robotAnswer = getRobatResult.getRobotAnswer().get(0);
        String question = robotAnswer.getAnsCon();
        String msgType = robotAnswer.getMsgType();
//        Log.e("AA", "-----问题答案-----" + getRobatResult.toString());
        if (Config.Type.RICHTEXT.equals(msgType)) {
            String moreContent = HtmlParser.getMoreContent(question);
            //设置富文本回答
            if (question.contains("class=\"wflink\"")) {
                //有附加问题
                GetRobatResult moreQuestionContent = HtmlParser.getMoreQuestionContent(question, getRobatResult);
                showTxtLeft(moreQuestionContent);
                tranTxtToSpeak(moreContent);
            } else {
                List<MyQuestion> relateList = robotAnswer.getRelateList();
                if (TextUtils.isEmpty(question)) {
                    robotAnswer.setAnsCon(getString(R.string.suggestion_word));
                }
                if (null != relateList) {
                    robotAnswer.setMsgType(Config.Type.TXT);
                    StringBuilder content = new StringBuilder();
                    String question_init = moreContent.replaceAll("<br", "");
                    String question_fin = question_init.replaceAll("/>", "");
//                    robotAnswer.setAnsCon(question_fin);
                    content.append(question_fin);
                    setFlowQuestion(getRobatResult, content);
                    moreContent = content.toString();
                }

                if (question.contains("<p>")) {
                    String s = moreContent.replaceAll("p>", "");
                    moreContent = s;
                }
                //无附加问题
                showTxtLeft(getRobatResult);
                tranTxtToSpeak(moreContent);
            }
        }
        //图片（包含 单图文，多图文）
        else if (Config.Type.IMAGE.equals(msgType)) {
            //设置image回答
            GetRobatResult imageContent = HtmlParser.getImageContent(question, getRobatResult);
            showTxtLeft(getRobatResult);
            tranTxtToSpeak(imageContent.getUrlRichText().toString());
        }
        //音频（播放音频）
        else if (Config.Type.VOICE.equals(msgType)) {
            //设置音频回答
            GetRobatResult videoContent = HtmlParser.getVideoContent(question, getRobatResult);
            getRobatResult.getRobotAnswer().get(0).setMsgType(Config.Type.VOICE);
            showTxtLeft(getRobatResult);
        }
        //文本 包含（多问题）
        else if (Config.Type.TXT.equals(msgType)) {
            if (question.contains("</")) {
                if (question.contains("class=\"wflink\"")) {
                    String moreContent = HtmlParser.getMoreContent(question);
                    GetRobatResult moreQuestionContent = HtmlParser.getMoreQuestionContent(question, getRobatResult);
                    showTxtLeft(moreQuestionContent);
                    tranTxtToSpeak(moreContent);
                } else if (question.equals(getString(R.string.url_nothinh_result))) {
                    String robot_question = getString(R.string.nothing_worf);
                    //设置文本回答
                    getRobatResult.getRobotAnswer().get(0).setAnsCon(robot_question);
                    showTxtLeft(getRobatResult);
                    //设置音频回答
                    tranTxtToSpeak(robot_question);
                } else {
                    String resultQuestion = "<body>" + question + "</body>";
                    String moreContent = HtmlParser.getMoreSelectContent(resultQuestion);
                    setVoiceSpeaker(getRobatResult, moreContent);
                }
            } else {
                if (TextUtils.isEmpty(question)) {
                    getRobatResult.getRobotAnswer().get(0).setAnsCon(getString(R.string.suggestion_word));
                    //设置音频回答
                    setVoiceSpeaker(getRobatResult);
                } else if (question.startsWith("$")) {
                    //播报并推出
                    String answer = question.substring(1, question.length());
                    getRobatResult.getRobotAnswer().get(0).setAnsCon(answer);
                    tranTxtToSpeak(answer);
                } else if (question.contains("<br")) {
                    String resutlt_one = question.replaceAll("<br", "");
                    String resutlt_two = resutlt_one.replaceAll(">", "");
                    getRobatResult.getRobotAnswer().get(0).setMsgType(Config.Type.RICHTEXT);
                    tranTxtToSpeak(resutlt_two);
                } else if (question.contains("&gt;") || question.contains("&lt;")) {
                    String resutlt_one = question.replaceAll("&gt;", ">");
                    String resutlt_two = resutlt_one.replaceAll("&lt;", "<");
                    String prespeak = resutlt_one.replaceAll("%&gt;", "");
                    String speak = prespeak.replaceAll("_&lt;%", "");
                    getRobatResult.getRobotAnswer().get(0).setAnsCon(resutlt_two);
                    tranTxtToSpeak(speak);
                } else {
                    //设置音频回答
                    setVoiceSpeaker(getRobatResult);
                    StringBuilder talkquestion = new StringBuilder();
                    talkquestion.append(question);
                    setFlowQuestion(getRobatResult, talkquestion);
                    tranTxtToSpeak(talkquestion.toString());
                }
                //设置文本回答
                showTxtLeft(getRobatResult);
            }
        }
    }

    private void setFlowQuestion(GetRobatResult getRobatResult, StringBuilder talkquestion) {
        List<MyQuestion> relateList = getRobatResult.getRobotAnswer().get(0).getRelateList();
        List<GusList> gusList = getRobatResult.getRobotAnswer().get(0).getGusList();
        if (null != relateList) {
            int size = relateList.size();
            if (size > 10) {
                for (int i = 0; i < 10; i++) {
                    talkquestion.append(relateList.get(i).getQuestion());
                }
            } else {

                for (int i = 0; i < size; i++) {
                    talkquestion.append(relateList.get(i).getQuestion());
                }
            }
        }
        if (null != gusList) {
            int size = gusList.size();

            if (size > 10) {
                for (int i = 0; i < 10; i++) {
                    talkquestion.append(gusList.get(i).getQuestion());
                }
            } else {
                for (int i = 0; i < size; i++) {
                    talkquestion.append(gusList.get(i).getQuestion());
                }
            }
        }
    }

    private void setVoiceSpeaker(GetRobatResult getRobatResult) {
        StringBuilder talkquestion = new StringBuilder();
        talkquestion.append(getString(R.string.suggestion_word));
        List<MyQuestion> relateList = getRobatResult.getRobotAnswer().get(0).getRelateList();
        List<GusList> gusList = getRobatResult.getRobotAnswer().get(0).getGusList();
        if (null != relateList) {
            int size = relateList.size();
            for (int i = 0; i < size; i++) {
                talkquestion.append(relateList.get(i).getQuestion());
            }
        }
        if (null != gusList) {
            int size = gusList.size();
            for (int i = 0; i < size; i++) {
                talkquestion.append(gusList.get(i).getSeedQuestion().getQuestion());
            }
        }
        tranTxtToSpeak(talkquestion.toString());
    }

    private void setVoiceSpeaker(GetRobatResult getRobatResult, String moreContent) {
        StringBuilder talkquestion = new StringBuilder();
        talkquestion.append(moreContent);
        List<MyQuestion> relateList = getRobatResult.getRobotAnswer().get(0).getRelateList();
        List<GusList> gusList = getRobatResult.getRobotAnswer().get(0).getGusList();
        if (null != relateList) {
            int size = relateList.size();
            for (int i = 0; i < size; i++) {
                talkquestion.append(relateList.get(i).getQuestion());
            }
        }
        if (null != gusList) {
            int size = gusList.size();
            for (int i = 0; i < size; i++) {
                talkquestion.append(gusList.get(i).getSeedQuestion().getQuestion());
            }
        }
        tranTxtToSpeak(talkquestion.toString());
        //设置文本回答
        showTxtLeft(getRobatResult);
    }

    /**
     * 左边显示文字
     */
    public void showTxtLeft(GetRobatResult getRobatResult) {
        getRobatResult.setDirect(Config.Direct.RECEIVE);
        getRobatResult.setTime(TimeUtil.getChatTimeStr());
        getRobatResult.setPlaying(true);//设置最后一个播放
        mAdapter.addData(getRobatResult);
        //跳转到最后一行
        mRecyclerView.smoothScrollToPosition(mAdapter.getTotalCount());
    }

    /**
     * 右边显示文字
     */
    public void showTxtRight(String content) {
        mAdapter.setWebViewDestory();
        if (null == content) {
            return;
        }
        ArrayList<RobotAnswer> robotAnswers = new ArrayList<>();
        GetRobatResult getRobatResult = new GetRobatResult();
//        getRobatResult.setDirect(1);
        getRobatResult.setTime(TimeUtil.getChatTimeStr());
        getRobatResult.setDirect(Config.Direct.SEND);
        RobotAnswer robotAnswer = new RobotAnswer();
        robotAnswer.setMsgType(Config.Type.TXT);
        robotAnswer.setAnsCon(content);
        robotAnswers.add(robotAnswer);
        getRobatResult.setRobotAnswer(robotAnswers);
        // TODO: 2017/10/31 清空adapter中的list数据
//        mAdapter.mList.clear();
        mAdapter.addData(getRobatResult);
        // TODO: 2017/10/31  跳转到最后一行
        mRecyclerView.smoothScrollToPosition(mAdapter.getTotalCount());
    }

    /**
     * 文字转播报
     */
    public void tranTxtToSpeak(final String question) {
        // TODO: 2017/11/3 如果在播报，就停止播报——聆听就停止聆听
        if (mTTSPlayer.isPlaying()) {
            mTTSPlayer.stop();
        }
        if(statue == Config.NUMNER_ONE || statue == Config.NUMNER_TWO || statue == Config.NUMNER_THREE){
            mUnderstander.cancel();
        }
        /**当前正在播报*/
        statue = Config.NUMNER_FOUR;
        mTTSPlayer.playText(question);
        handler.sendEmptyMessage(Config.NUMNER_THREE);
    }
}

