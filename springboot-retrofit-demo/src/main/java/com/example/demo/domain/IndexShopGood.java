package com.example.demo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndexShopGood {

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getGnum() {
		return gnum;
	}

	public void setGnum(Integer gnum) {
		this.gnum = gnum;
	}

	@Expose
	@SerializedName("lon")
	private Double lon;

	@Expose
	@SerializedName("lat")
	private Double lat;

	@Expose
	@SerializedName("num")
	private Integer num; // 店铺 或者旗舰店个数

	@Expose
	@SerializedName("gnum")
	private Integer gnum; // 商品个数
	
}
