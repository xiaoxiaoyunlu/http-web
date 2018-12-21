package com.zj.server.http.resp;

import org.apache.commons.lang3.StringUtils;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

public class ConstantResponseReactor implements HttpReactor {

	public ConstantResponseReactor() {
	
	}
	
	public ConstantResponseReactor(HttpResponse response){
		nextReactor = null;
		responseSender = new DefaultHttpResponseSender();
		this.response = response;
	}
	
	
	@Override
	public void onHttpRequest(Channel channel, HttpRequest request) {
		if(null != request){
			String uuid = request.headers().get("uuid");
			if(!StringUtils.isBlank(uuid)){
				response.headers().set("uuid",uuid);
			}
		}
		responseSender.sendResponse(channel, response);
		if(null != nextReactor){
			nextReactor.onHttpRequest(channel,request);
		}
        
	}
	
	public HttpReactor getNextReactor() {
		return nextReactor;
	}

	public void setNextReactor(HttpReactor nextReactor) {
		this.nextReactor = nextReactor;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public HttpResponseSender getResponseSender() {
		return responseSender;
	}

	public void setResponseSender(HttpResponseSender responseSender) {
		this.responseSender = responseSender;
	}

	private HttpReactor nextReactor;
    private HttpResponse response;
    private HttpResponseSender responseSender;

}
