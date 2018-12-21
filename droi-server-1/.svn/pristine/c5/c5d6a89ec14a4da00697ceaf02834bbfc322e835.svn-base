package com.zj.server.http.resp;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * 响应体
 * @author USER
 *
 */
public class ConstantResponse {

	private ConstantResponse(){
		
	}
	
	public static DefaultFullHttpResponse getResponse200Nobody() {
		return RESPONSE_200_NOBODY;
	}

	public static DefaultFullHttpResponse getResponse400Nobody() {
		return RESPONSE_400_NOBODY;
	}

	public static DefaultFullHttpResponse getResponseServerBusy() {
		return RESPONSE_SERVER_BUSY;
	}

	public static DefaultFullHttpResponse getResponse404NotFound() {
		return RESPONSE_404_NOT_FOUND;
	}

	public static DefaultFullHttpResponse getResponseGatewayTimeout() {
		return RESPONSE_GATEWAY_TIMEOUT;
	}

    public static DefaultFullHttpResponse get200WithContentTypeResponse(String contentType){
     	 DefaultFullHttpResponse response = RESPONSE_200_NOBODY;
     	 response.headers().set(HttpHeaderNames.CONTENT_TYPE,contentType);
     	 return response;
    }

	public static final DefaultFullHttpResponse RESPONSE_200_NOBODY;
    public static final DefaultFullHttpResponse RESPONSE_400_NOBODY;
    public static final DefaultFullHttpResponse RESPONSE_SERVER_BUSY;
    public static final DefaultFullHttpResponse RESPONSE_404_NOT_FOUND;
    public static final DefaultFullHttpResponse RESPONSE_GATEWAY_TIMEOUT;
    
    static{
    	RESPONSE_200_NOBODY = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.wrappedBuffer("OK".getBytes()));
    	RESPONSE_400_NOBODY = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
    	RESPONSE_SERVER_BUSY = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SERVICE_UNAVAILABLE,Unpooled.wrappedBuffer("Servet too busy".getBytes()));
    	RESPONSE_404_NOT_FOUND = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
    	RESPONSE_GATEWAY_TIMEOUT = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.GATEWAY_TIMEOUT);
    }
}
