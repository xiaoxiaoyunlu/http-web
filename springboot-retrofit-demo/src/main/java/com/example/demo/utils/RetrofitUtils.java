package com.example.demo.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import com.example.demo.api.HotelAPI;
import com.example.demo.domain.BaseReq;
import com.example.demo.domain.IndexShopGood;
import com.example.demo.interceptor.ManoConverterFactory;
import com.example.demo.interceptor.StringCallAdapterFactory;
import com.example.demo.interceptor.StringConverterFactory;

/**
 * Retrofit工具类
 */
public class RetrofitUtils {
    public static final String SERVER_URL = "http://localhost:26711";
    /**
     * 超时时间
     */
    public static final int TIMEOUT = 60;
    private static volatile RetrofitUtils mInstance;
    private Retrofit mRetrofit;

    public static RetrofitUtils getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtils.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtils();
                }
            }
        }
        return mInstance;
    }

    private RetrofitUtils() {
        initRetrofit();
    }

    /**
     * 初始化Retrofit
     */
    private void initRetrofit() {
    	OkHttpClient.Builder builder = new OkHttpClient.Builder();
		// 设置超时
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
//        builder.addInterceptor((Interceptor) new HttpResponseInterceptor());
        
        OkHttpClient client = builder.build();

        mRetrofit = new Retrofit.Builder().baseUrl(SERVER_URL)
				.addConverterFactory(ManoConverterFactory.create())
//				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        		.addCallAdapterFactory(new StringCallAdapterFactory())
//                .addConverterFactory(new StringConverterFactory())
				.client(client)
				.build();
    }

    /**
     * 创建API
     */
    public <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }
}

