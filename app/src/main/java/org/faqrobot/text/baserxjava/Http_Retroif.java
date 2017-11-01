package org.faqrobot.text.baserxjava;

import android.text.TextUtils;

import org.faqrobot.text.MyApplication;
import org.faqrobot.text.constant.Config;
import org.faqrobot.text.netapi.Chat2Server;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 孟晨 on 2017/10/25.
 */

public class Http_Retroif {
    private static final int DEFAULT_TIMEOUT = 3;
    private final Chat2Server chat2Server;

    private Http_Retroif() {
        File cacheFile = new File(MyApplication.getContext().getCacheDir(), "response");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .cache(cache);
        OkHttpClient build = builder.build();
        //Retrofit的声明（网络请求）
        Retrofit mbuild = new Retrofit.Builder()
                .baseUrl(Config.HOSTNAME)
                .client(build)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //数据的类型
        chat2Server = mbuild.create(Chat2Server.class);
    }

    public Chat2Server geterver() {
        return chat2Server;
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final Http_Retroif INSTANCE = new Http_Retroif();
    }

    //获取单例
    public static Http_Retroif getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=43200";
            }
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
    };
}

