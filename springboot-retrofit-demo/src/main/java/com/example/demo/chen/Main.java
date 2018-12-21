package com.example.demo.chen;

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.ietf.jgss.Oid;

public class Main {
    private static String SERVER_URL = "http://localhost:26711/";
    private static ServiceApi service;

    public static void main(String... args){
//        System.out.print("haha");
        initNet();
//        getMainShop();
        userGetSubUser();
       try {
		Thread.sleep(100000); 
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
    }

    public static void userGetSubUser(){
    	subscribe(service.post(new UserGetSubUser()));
    }
    
    public static void userGetPackgetGood(){
    	subscribe(service.post(new UserGetPackageGood()));
    }
    
    public static void getMainShop(){
        subscribe(service.post(new NetMainShopAndGoodsList()));
    }
    
    public static void userAndUpdate(){
    	subscribe(service.post(new UserandUpdate()));
    }
    
    public static void updateSKu(){
    	subscribe(service.post(new UploadSKu()));
    }
    
    public static void register(){
    	subscribe(service.post(new Register()));
    }

    public static void subscribe(Observable observable) {
        System.out.print("subscribe");
        observable.subscribeOn(Schedulers.newThread())
                .subscribe(new SuccessfulCallback(), new FailCallback());
    }

    public static void initNet(){
        List protocolList = new  ArrayList< Protocol >();
        protocolList.add(Protocol.HTTP_1_1);
        protocolList.add(Protocol.HTTP_2);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor(){

                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Request newRequest = request.newBuilder().header("connection", "close").build();
                        System.out.print("request time:"+(new Date(System.currentTimeMillis()).toString()));
                        Response response = chain.proceed(newRequest);
                        MediaType mediaType = response.body().contentType();
                        byte[] bytes = response.body().bytes();
                        String str= "";
                        try {
                            str = new String(DESUtil.decrypt(bytes, DESUtil.ENCRYPT_KEY));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.print("intercept code:"+ str);
                        return response.newBuilder().body(ResponseBody.create(mediaType,str.trim())).build();
                    }
                })                .retryOnConnectionFailure(false)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .protocols(protocolList)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(ManoConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(ServiceApi.class);
    }

    private static Gson gson = new Gson();
    public static String bean2Str(Object obj){
        return gson.toJson(obj);
    }

    public static class SuccessfulCallback implements Consumer<ResponseBase> {
        @Override
        public void accept(ResponseBase response) throws Exception {
            System.out.print("SuccessfulCallback");
        }
    }

    public static class FailCallback implements Consumer<Throwable> {
        @Override
        public void accept(Throwable throwable) throws Exception {
            System.out.print("FailCallback");
        }
    }
}
