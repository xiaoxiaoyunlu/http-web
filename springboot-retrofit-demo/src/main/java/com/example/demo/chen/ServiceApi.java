package com.example.demo.chen;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;



public interface ServiceApi {
    @POST(".")
    public Observable<ResponseBase> post(@Body Object obj);
}
