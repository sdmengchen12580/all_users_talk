package org.faqrobot.text.ui.mactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.faqrobot.text.R;
import org.faqrobot.text.constant.Config;

import java.io.File;

public class UpPersonInfoActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    private Button button_up_img;
    private ImageView img_takephoto;
    private EditText edittext_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_person_info);
        /**初始化控件*/
        initView();
        /**获取屏幕的宽高*/
        attain_screen_width_or_height();
        /**初始化手势监听*/
        CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        gestureDetector = new GestureDetector(this, customGestureDetector);
    }

    private void initView() {
        edittext_username = (EditText) findViewById(R.id.et_username);
        button_up_img = (Button) findViewById(R.id.button_up);
        img_takephoto = (ImageView) findViewById(R.id.img_takephoto);
        button_up_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpPersonInfoActivity.this, "点击上传", Toast.LENGTH_SHORT).show();
            }
        });
        img_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }

    /**
     * 拍照获取图片
     */
    Uri photoUri;

    private void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(openCameraIntent, Config.NUMNER_ONE);
    }

    /**
     * 设置图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 如果返回码是可以用的——里面用switch判断requestCode
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //不论是选择本地的相册还是现场拍摄，之后的步骤都是跳转到裁剪图片
                case Config.NUMNER_ONE:
                    startPhotoZoom(photoUri);
                    break;
                //裁剪的请求码
                case Config.NUMNER_TWO:
                    if (data != null) {
                        setImageToView(data);
                    }
                    break;
            }
        }
    }

    //设置裁剪后的图片到控件上
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            //固定格式——将Bundle对象中图片资源转化成bitmap的方法
            Bitmap photo = extras.getParcelable("data");
            img_takephoto.setImageBitmap(photo);
        }
    }

    //裁剪图片方法实现
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        photoUri = uri;
        //跳到手机自带的裁剪页面——并设置数据的资源路径和资源的类型
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例————1比1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Config.NUMNER_TWO);//裁剪的请求码
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
            }
            /**右滑*/
            else if (endX - beginX > minMove && Math.abs(velocityX) > minVelocity) {
            }
            /**大于屏幕的五分之四才上滑OK*/
            else if (beginY - endY > minMove && Math.abs(velocityY) > minVelocity && beginY > screenHeigh * 4 / 5) {
                Log.e("chatfragment:", "上滑");
            }
            /**下滑*/
            else if (endY - beginY > minMove && Math.abs(velocityY) > minVelocity && beginY < screenHeigh * 4 / 5) {
                Log.e("chatfragment:", "下滑");
                startActivity(new Intent(UpPersonInfoActivity.this, TabActivity.class));
                overridePendingTransition(R.anim.enter_chat, R.anim.leave_chat);
                finish();
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
}
