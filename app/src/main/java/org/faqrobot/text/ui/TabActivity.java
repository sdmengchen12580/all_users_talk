package org.faqrobot.text.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.faqrobot.text.R;
import org.faqrobot.text.constant.Config;
import org.faqrobot.text.ui.tabfragment.ChatFragment;
import org.faqrobot.text.ui.tabfragment.MessageFragment;
import org.faqrobot.text.ui.tabfragment.RelQuesstionsFragment;
import org.faqrobot.text.view.ChildViewPager;

import java.util.ArrayList;

public class TabActivity extends AppCompatActivity {


    public ChildViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        /**检测网络*/
        hideBottomNagative();
        initView();
        attain_screen_width_or_height();
        initGesture();
    }

    private void hideBottomNagative() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void initView() {
        viewPager = (ChildViewPager) findViewById(R.id.viewPager);
        /**
         * viewPager添加fragment适配器
         * viewPager监听->Button切换
         */
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(listener);
        /**先显示聊天界面*/
        viewPager.setCurrentItem(1,true);
    }


    /**
     * 3个fragment的管理类
     */
    public static ChatFragment chatFragment=new ChatFragment();
    public static MessageFragment messageFragment=new MessageFragment();
    public static RelQuesstionsFragment relQuesstionsFragment=new RelQuesstionsFragment();
    private FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return messageFragment;
                case 1:
                    return chatFragment;
                case 2:
                    return relQuesstionsFragment;
                default:
                    throw new RuntimeException("未知错误");
            }
        }
        @Override
        public int getCount() {
            return 3;
        }
    };

    /**
     *   viewPager监听->Button切换
     */
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        /**当前选中的fragment*/
        @Override
        public void onPageSelected(int position) {
            /**不在聊天界面，状态切换为死亡状态。清空data的数据*/
                if (position!=1){
                    if(chatFragment.statue== Config.NUMNER_FOUR){
                        chatFragment.mTTSPlayer.stop();
                        chatFragment.statue = Config.NUMNER_FIVE;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chatFragment.txview_status.setText("点击说话");
                                chatFragment.mVolume.setProgress(0);
                            }
                        });
                    }
                    else if(chatFragment.statue==Config.NUMNER_ONE||chatFragment.statue==Config.NUMNER_TWO
                            ||chatFragment.statue==Config.NUMNER_THREE){
                        chatFragment.mUnderstander.cancel();
                        chatFragment.statue = Config.NUMNER_FIVE;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chatFragment.txview_status.setText("点击说话");
                                chatFragment.mVolume.setProgress(0);
                            }
                        });
                    }
                }else{
                    /**让线程一直运行着*/
                    chatFragment.checking_is_idel = true;
                    chatFragment.checking_is_speck = true;
                }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    /**
     * 获取屏幕的宽高
     */
    int screenWidth;
    int screenHeigh;
    public void attain_screen_width_or_height() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
    }

    /**
     * 初始化手势
     */
    private void initGesture() {
        //实例化SimpleOnGestureListener与GestureDetector对象
        mgListener = new MyGestureListener();
        mDetector = new GestureDetector(this, mgListener);
    }


    /**
     * 自定义一个GestureListener
     */
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
//            if (e1.getY() > screenHeigh * 4 / 5 && e1.getY() - e2.getY() > MIN_MOVE) {
//                Toast.makeText(TabActivity.this, "下滑跳转到下个界面", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(TabActivity.this, UpPersonInfoActivity.class));
//                finish();
//            }
            return true;
        }
    }

    /**
     * 手势监听
     */
    private GestureDetector mDetector;
    private final static int MIN_MOVE = 100;   //最小距离
    private MyGestureListener mgListener;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }



    /**********************************存接口的集合**********************************************/
    public interface MyOnTouchListener {
         boolean onTouch(MotionEvent ev);
    }

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<>(1);
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            if (listener != null) {
                listener.onTouch(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }
}