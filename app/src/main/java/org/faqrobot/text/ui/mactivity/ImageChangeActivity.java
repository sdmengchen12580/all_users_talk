package org.faqrobot.text.ui.mactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import org.faqrobot.text.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ImageChangeActivity extends AppCompatActivity {


    @BindView(R.id.roll_view_pager)
    RollPagerView mRollViewPager;
    @BindView(R.id.img)
    ImageView img;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        /**全屏*/
        hideBottomNagative();
        /**获取屏幕的宽高*/
        attain_screen_width_or_height();
        /**viewpager的设置*/
        set_viewpager();
        /**初始化手势*/
        initGesture();
        /**动画加图片跳转*/
        initImg_to_change_activity();
    }

    private void initImg_to_change_activity() {
        AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.img_up_and_down);
        img.startAnimation(animationSet);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ImageChangeActivity.this, TabActivity.class));
                onDestroy();
            }
        });
    }


    /**
     * viewpager的设置
     */
    private void set_viewpager()
    {
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new TestNormalAdapter());
        //自定义指示器图片
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        //设置圆点指示器颜色
        mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
    }

    private class TestNormalAdapter extends StaticPagerAdapter
    {
        //放图片的数组——可以写成biemap的数组
        private int[] imgs = {

        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }


    private void hideBottomNagative()
    {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 获取屏幕的宽高
     */
    int screenWidth;
    int screenHeigh;
    public void attain_screen_width_or_height()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
    }

    /**
     * 初始化手势
     */
    private void initGesture()
    {
        //实例化SimpleOnGestureListener与GestureDetector对象
        mgListener = new MyGestureListener();
        mDetector = new GestureDetector(this, mgListener);
    }


    /**
     * 自定义一个GestureListener
     */
    // TODO: 2017/10/26 滑动冲突
    // TODO: 2017/10/26 滑动冲突
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
            if (e1.getY() > screenHeigh * 4 / 5 && e1.getY() - e2.getY() > MIN_MOVE) {
                Toast.makeText(ImageChangeActivity.this, "下滑跳转到下个界面", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ImageChangeActivity.this, TabActivity.class));
                onDestroy();
            }
            return false;
        }
    }

    /**
     * 手势监听
     */
    private GestureDetector mDetector;
    private final static int MIN_MOVE = 100;   //最小距离
    private MyGestureListener mgListener;

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return mDetector.onTouchEvent(event);
    }

}
