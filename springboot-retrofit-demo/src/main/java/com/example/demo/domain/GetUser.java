package com.example.demo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUser {

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Expose
	@SerializedName("openid")
	private String open_id;

	@Expose
	@SerializedName("token")
	private String token;
}
