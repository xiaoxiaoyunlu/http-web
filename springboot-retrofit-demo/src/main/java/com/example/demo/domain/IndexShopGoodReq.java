package com.example.demo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IndexShopGoodReq {
	
	public GetHeader getHead() {
		return head;
	}

	public void setHead(GetHeader head) {
		this.head = head;
	}

	public IndexShopGood getBody() {
		return body;
	}

	public void setBody(IndexShopGood body) {
		this.body = body;
	}

	@Expose
	@SerializedName("head")
	private GetHeader head = new GetHeader();
	
	@Expose
	@SerializedName("body")
	private IndexShopGood body;

}
