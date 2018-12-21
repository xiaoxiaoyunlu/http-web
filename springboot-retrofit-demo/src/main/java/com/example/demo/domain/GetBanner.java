package com.example.demo.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBanner {
	
	public Long getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(Long activity_id) {
		this.activity_id = activity_id;
	}

	@Expose
	@SerializedName("activity_id")
	private Long activity_id;

}
