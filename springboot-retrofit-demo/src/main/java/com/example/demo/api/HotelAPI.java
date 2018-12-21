package com.example.demo.api;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import com.example.demo.domain.BaseReq;
import com.example.demo.domain.GetBanner;
import com.example.demo.domain.GetUser;
import com.example.demo.domain.Hotel;
import com.example.demo.domain.IndexShopGood;
import com.example.demo.domain.IndexShopGoodReq;

public interface HotelAPI {
    
    /**
     * GET请求带查询参数
     */
    @GET("/hotel/getHotelWithQueryParameter")
    public String getHotelWithQueryParameter(@Query("hotelname") String hotelname);
    
    /**
     * POST请求
     */
    @POST("/hotel/getHotelList")
    public String getHotelList();
    
    /**
     * POST请求,带参数JavaBeane
     */
    @POST("/hotel/getHotelListWithBody")
    public List<Hotel> getHotelListWithBody(@Body Hotel hotel);
    
    //获取首页
    @POST(".")
    public String getIndexShopGood(@Body BaseReq<IndexShopGood> req);
    
  //获取首页
    @POST(".")
    public String getIndexShopGood0(@Body IndexShopGoodReq req);
    
    //获取首页
    @POST(".")
    public String getBannner(@Body BaseReq<GetBanner> req);
    
  //获取首页
    @POST(".")
    public String getUser(@Body BaseReq<GetUser> req); 
    
}