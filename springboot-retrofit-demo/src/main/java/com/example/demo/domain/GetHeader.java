package com.example.demo.domain;

import java.util.UUID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetHeader {
	
	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMcd() {
		return mcd;
	}

	public void setMcd(String mcd) {
		this.mcd = mcd;
	}

	public Long getMsb() {
		return msb;
	}

	public void setMsb(Long msb) {
		this.msb = uuid.getMostSignificantBits();
	}

	public Long getLsb() {
		return lsb;
	}

	public void setLsb(Long lsb) {
		this.lsb = uuid.getLeastSignificantBits();
	}

	@Expose
	@SerializedName("ver")
	private Integer ver =1;
	
	@Expose
	@SerializedName("type")
	private Integer type=1;
	
	@Expose
	@SerializedName("mcd")
	private String mcd;
	
	@Expose
	@SerializedName("msb")
	private Long msb;
	
	@Expose
	@SerializedName("lsb")
	private Long lsb;
	
	private UUID uuid=UUID.randomUUID();

}
