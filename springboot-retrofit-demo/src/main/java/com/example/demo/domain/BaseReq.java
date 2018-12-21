package com.example.demo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseReq<T> {

	public GetHeader getHead() {
		return head;
	}

	public void setHead(GetHeader head) {
		this.head = head;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	@Expose
	@SerializedName("head")
	private GetHeader head= new GetHeader();
	
	@Expose
	@SerializedName("body")
	private  T t;
}
