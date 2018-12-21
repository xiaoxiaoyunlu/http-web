package com.zj.server.transport;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.AttributeKey;
import io.netty.util.DefaultAttributeMap;
import com.zj.server.common.Propertyable;
import com.zj.server.http.endpoint.Endpoint;
import com.zj.server.transform.Sender;
/**
 * request 和  response 的 持有工具类
 *  
 * @author USER
 *
 */
public class TransportUtil {
	
	private static final String TRANSPORT_SENDER = "TRANSPORT_SENDER";
	private static final String HTTP_REQUEST = "HTTP_REQUEST";
	private static final String HTTP_ENDPOINT = "HTTP_ENDPOINT";
	//netty 4 中ChannelLocal和Channel.attachment被移除，AttributeMap
//	public static final ChannelLocal endpoints = new ChannelLocal();
	//废弃  Channel 继承了AttributeMap  自带功能
	public static final DefaultAttributeMap endpoints = new DefaultAttributeMap();
	
	public TransportUtil(){
		
	}
	
	public static void attachEndpointToSession(Channel channel,Endpoint endpoint){
        channel.attr(AttributeKey.valueOf(HTTP_ENDPOINT)).set(endpoint);
	}

	public static Endpoint getEndpointOfSession(Channel channel){
		return (Endpoint) channel.attr(AttributeKey.valueOf(HTTP_ENDPOINT)).get();
	}
	
	public static Object attachSender(Object propertyable,Sender sender){
		if(propertyable instanceof Propertyable){
			((Propertyable)propertyable).setProperty(TRANSPORT_SENDER, sender);
		}
		return propertyable;
	}
	
	public static Sender getSenderOf(Object propertyable){
		if(propertyable instanceof Propertyable){
			return (Sender) ((Propertyable)propertyable).getProperty(TRANSPORT_SENDER);
		}
		return null;
	}
	
	public static Object attachRequest(Object propertyable,HttpRequest request){
		
		if(propertyable instanceof Propertyable){
			((Propertyable)propertyable).setProperty(HTTP_REQUEST, request);
		}
		return propertyable;
	}
	
	public static HttpRequest getRequestOf(Object propertyable){
		if(propertyable instanceof Propertyable){
			return (HttpRequest) ((Propertyable)propertyable).getProperty(HTTP_REQUEST);
		}
		return null;
	}
}
