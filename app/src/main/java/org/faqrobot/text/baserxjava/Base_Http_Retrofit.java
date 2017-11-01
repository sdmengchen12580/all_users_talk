package org.faqrobot.text.baserxjava;

import android.text.TextUtils;

import org.faqrobot.text.MyApplication;
import org.faqrobot.text.constant.Config;
import org.faqrobot.text.netapi.Rxjava_Api;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yunwen on 2017/10/9.
 */

public class Base_Http_Retrofit {

    private static final int DEFAULT_TIMEOUT = 3;
    private OkHttpClient okHttpClient;
    private Rxjava_Api rxjava_api;

    /**在访问HttpMethods时创建单例——通过静态内部类去实例化类*/
    private static class SingletonHolder {
        private static final Base_Http_Retrofit INSTANCE = new Base_Http_Retrofit();
    }

    /**单例*/
    public static Base_Http_Retrofit getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private Base_Http_Retrofit(){
        File cacheFile = new File(MyApplication.getContext().getCacheDir(), "response");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        /**拦截器*/
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /**共用okhttp*/
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(interceptor)
                //             .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cache(cache)
                .build();
        /**共用Retrofit*/
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(Config.HOSTNAME)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        rxjava_api = mRetrofit.create(Rxjava_Api.class);
    }

    /**对外提供获取接口对象的方法*/
    public Rxjava_Api geterver() {
        return rxjava_api;
    }

    /**自定义日志拦截器*/
    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=43200";
            }
            /**
             *     网页的缓存是由HTTP消息头中的“Cache-control”来控制的
             *     常见的取值有private、no-cache、max-age、must-revalidate等，默认为private。
             *    如果指定cache-control的值为private、no-cache、must-revalidate，那么打开新窗口访问时都会重新访问服务器。
             *     max-age=43200
             */
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
    };
}
