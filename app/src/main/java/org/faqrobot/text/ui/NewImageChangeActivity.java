package org.faqrobot.text.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import org.faqrobot.text.R;

public class NewImageChangeActivity extends AppCompatActivity {


    private ViewFlipper mViewFlipper;
    private GestureDetector gestureDetector;
    private CustomGestureDetector customGestureDetector;
    int[] resources = {
            R.drawable.imgone,
            R.drawable.imgtwo,
            R.drawable.imgthree,
//            R.drawable.image_change_one,
//            R.drawable.image_change_two,
//            R.drawable.image_change_three
    };

    private ImageView[] imageView = new ImageView[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_image_change);
        mViewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        for (int i = 0; i < resources.length; i++) {
            imageView[i]= new ImageView(this);
            imageView[i].setImageResource(resources[i]);
            imageView[i].setScaleType(ImageView.ScaleType.FIT_XY);
            mViewFlipper.addView(imageView[i]);
        }
        /**5s自动切换*/
        // TODO: 2017/10/30 多滑动一张 
//        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(5000);
        /**获取屏幕的宽高*/
        attain_screen_width_or_height();
        /**初始化手势监听*/
        customGestureDetector = new CustomGestureDetector();
        gestureDetector = new GestureDetector(this, customGestureDetector);
    }

    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageView = null;
        resources = null;
        mViewFlipper = null;
        customGestureDetector = null;
        gestureDetector= null;
    }
}
