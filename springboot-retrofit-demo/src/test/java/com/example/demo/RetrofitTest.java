package com.example.demo;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import retrofit2.Callback;

import com.example.demo.api.HotelAPI;
import com.example.demo.chen.ResponseBase;
import com.example.demo.domain.BaseReq;
import com.example.demo.domain.GetBanner;
import com.example.demo.domain.GetUser;
import com.example.demo.domain.Hotel;
import com.example.demo.domain.IndexShopGood;
import com.example.demo.utils.RetrofitUtils;

public class RetrofitTest extends BaseTest {

	private HotelAPI api;

	@Before
	public void init() throws Exception {
		RetrofitUtils retrofitUtils = RetrofitUtils.getInstance();
		api = retrofitUtils.create(HotelAPI.class);
	}

	@Test
	public void getHotel() throws Exception {
		String hotelList = api.getHotelList();
		System.out.println("11111");
	}

	@Test
	public void getIndexSHopGood() throws Exception {

		IndexShopGood indexShopGood = new IndexShopGood();
		indexShopGood.setGnum(3);
		indexShopGood.setNum(10);
		indexShopGood.setLat(31.194214);
		indexShopGood.setLon(121.442446);
		BaseReq<IndexShopGood> indexshopBaseReq = new BaseReq<IndexShopGood>();
		indexshopBaseReq.getHead().setMcd("100034");
		indexshopBaseReq.setT(indexShopGood);
		String indexShopGood2 = api.getIndexShopGood(indexshopBaseReq);

		GetBanner banner = new GetBanner();
		banner.setActivity_id(1L);

		BaseReq<GetBanner> getBannerReq = new BaseReq<GetBanner>();
		getBannerReq.setT(banner);
		getBannerReq.getHead().setMcd("100083");
		String bannner = api.getBannner(getBannerReq);
		GetUser user = new GetUser();

		BaseReq<GetUser> getUserReq = new BaseReq<GetUser>();
		getUserReq.setT(user);
		getBannerReq.getHead().setMcd("100059");
		String user2 = api.getUser(getUserReq);
	}
}
