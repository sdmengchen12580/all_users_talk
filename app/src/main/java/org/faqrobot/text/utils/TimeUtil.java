package org.faqrobot.text.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.text.format.Time;

import org.faqrobot.text.MyApplication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 孟晨 on 2017/10/25.
 */

public class TimeUtil {
    private static Subscription mTimeSubscribe;

    /**
     * 时间转化为聊天界面显示字符串
     */
    public static String getChatTimeStr() {
        long time = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * 通过经纬度获取网络时间
     *
     * @return 网络时间
     */
    public static long getNetCurrentTime() {
        Context application = MyApplication.getContext();
        LocationManager systemService = (LocationManager) application.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //没有权限则返回系统当前时间
            return System.currentTimeMillis();
        }
        Location lastKnownLocation = systemService.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //返回网络当前时间
        return lastKnownLocation.getTime();
    }

    //传递当前事件截的接口
    public interface OnNetTimeStamp {
        void getTimeStamp(long timeStamp);

        void getSystemStamp(long timeStamp);
    }

    /**
     * 销毁时间的订阅
     */
    public static void onDestoryNetTimeStampSubscribe() {
        if (null != mTimeSubscribe && !mTimeSubscribe.isUnsubscribed())
            mTimeSubscribe.unsubscribe();
    }


    /**
     * 通过百度获取网络时间截
     *
     * @param onNetTimeSamp 获取网络时间戳的接口
     */
    public static void getNetTimeStamp(final OnNetTimeStamp onNetTimeSamp) {
//        if (null != mTimeSubscribe && !mTimeSubscribe.isUnsubscribed())
//            mTimeSubscribe.unsubscribe();
        //获取当前网络的时间戳
        mTimeSubscribe = Observable.just("http://www.baidu.com")
                .map(new Func1<String, Long>() {
                    @Override
                    public Long call(String s) {
                        return getWebsiteDatetime(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        onNetTimeSamp.getTimeStamp(System.currentTimeMillis());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        //获取当前网络的时间戳
                        onNetTimeSamp.getTimeStamp(aLong);
                    }
                });


    }

    private static long getWebsiteDatetime(String webUrl) {
        long ld = 0;
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            ld = uc.getDate();// 读取网站日期时间
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ld;
    }


    public static int getCurrentDay() {
        int currentDay;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar cal = Calendar.getInstance();
            int day_of_year = cal.get(Calendar.DAY_OF_YEAR);
            int year = cal.get(Calendar.YEAR);
            currentDay = day_of_year + year;
        } else {
            Time time = new Time();
            int yearDay = time.yearDay;
            int year = time.year;
            currentDay = year + yearDay;
        }
        return currentDay;
    }


    public static Observable<Integer> countdown(int time) {
        if (time < 0) time = 0;

        final int countTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Long, Integer>() {
                    @Override
                    public Integer call(Long increaseTime) {
                        return countTime - increaseTime.intValue();
                    }
                })
                .take(countTime + 1);

    }
}
