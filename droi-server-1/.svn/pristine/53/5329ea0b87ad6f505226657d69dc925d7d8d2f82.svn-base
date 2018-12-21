package com.zj.server.http.resp;

import java.io.UnsupportedEncodingException;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * Http response 发送 接口
 * @author USER
 *
 */
public interface HttpResponseSender {
	
	void sendResponse(Channel channel, HttpResponse httpresponse);
	
	void sendResponse(Channel channel, HttpResponseStatus httpresponsestatus, String responseContent);
	
	void sendResponse(Channel channel, HttpResponseStatus httpresponsestatus, String responseContent, String charsetName)
	        throws UnsupportedEncodingException;
	
	void sendRedirectResponse(Channel channel, String redirectUrl);
	
	String sendFile(Channel channel, byte fullContent[], int startPos, int endPos);

}
