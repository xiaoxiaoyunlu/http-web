package com.zj.server.http.resp;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultHttpResponseSender implements HttpResponseSender {
	
	public DefaultHttpResponseSender() {}

	@Override
	public void sendResponse(Channel channel, HttpResponse response) {
        if(null == channel){
        	log.warn("send response, but the channel is closed, responseName=[{}]", response.getClass());
            return;
        }
        ChannelFuture future = channel.writeAndFlush(response);
        
        if(!response.headers().contains(HttpHeaderNames.CONNECTION) || !response.headers().contains(HttpHeaderNames.CONTENT_LENGTH)){
        	future.addListener(ChannelFutureListener.CLOSE);
        }
	}

	@Override
	public void sendResponse(Channel channel,
			HttpResponseStatus httpresponsestatus, String responseContent) {
		try {
			sendResponse(channel, httpresponsestatus, responseContent,CharsetUtil.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void sendResponse(Channel channel,
			HttpResponseStatus httpresponsestatus, String responseContent, String charsetName)
			throws UnsupportedEncodingException {
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpresponsestatus,Unpooled.wrappedBuffer(responseContent.getBytes(charsetName)));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,
                response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
	    sendResponse(channel,response);
	    
	}

	@Override
	public void sendRedirectResponse(Channel channel, String redirectUrl) {
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.TEMPORARY_REDIRECT);
		response.headers().set("Location", redirectUrl);
		sendResponse(channel, response);

	}

	@Override
	public String sendFile(Channel channel, byte fullContent[], int startPos, int endPos) {
		HttpResponseStatus httpResponseStatus = startPos<=0 && endPos<=0 ? HttpResponseStatus.OK:HttpResponseStatus.PARTIAL_CONTENT;
		if(startPos < 0 || startPos > fullContent.length){
			startPos = 0;
		}
		if(endPos < startPos || endPos <= 0 || endPos > fullContent.length){
			endPos = fullContent.length;
		}
		byte[] partialContent = Arrays.copyOfRange(fullContent, startPos, endPos+1);
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, httpResponseStatus,Unpooled.wrappedBuffer(partialContent));
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, partialContent.length);
		String range = new StringBuilder("bytes ").append(startPos).append("-").append(endPos).append("/").append(fullContent.length).toString();
		response.headers().set(HttpHeaderNames.CONTENT_RANGE, range);
		sendResponse(channel, response);
		return httpResponseStatus.equals(HttpResponseStatus.PARTIAL_CONTENT)? range:null;
	}

}
