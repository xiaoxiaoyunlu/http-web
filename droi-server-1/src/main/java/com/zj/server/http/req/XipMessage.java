package com.zj.server.http.req;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class XipMessage {

	@Expose
	@SerializedName("head")
	private String xipHeader;
	@Expose
	@SerializedName("body")
	private String xipBody;

	public String getXipHeader() {
		return xipHeader;
	}

	public void setXipHeader(String xipHeader) {
		this.xipHeader = xipHeader;
	}

	public String getXipBody() {
		return xipBody;
	}

	public void setXipBody(String xipBody) {
		this.xipBody = xipBody;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public static void main(String[] args) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		XipMessage msg = new XipMessage();
		XipHeader head = new XipHeader();
		head.setMessageCode(10001);

		String str = gson.toJson(msg);
		System.out.println(str);
		XipMessage msg1 = (XipMessage) gson.fromJson(str, XipMessage.class);
		System.out.println(msg1);
	}
}
