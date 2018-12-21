package com.zj.server.http.endpoint;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpResponse;

import com.zj.server.common.DefaultHolder;
import com.zj.server.common.Holder;
import com.zj.server.transform.Receiver;
import com.zj.server.transform.Transformer;

public class DefaultEndpointFactory implements EndpointFactory {

	public DefaultEndpointFactory() {
		messageClosure = null;
		responseContext = new DefaultHolder();
	}
	
	
	
	@Override
	public Endpoint createEndpoint(Channel channel,
			Transformer<Object, HttpResponse> responseEncoder) {
		ServerEndpoint endpoint = new ServerEndpoint();
		endpoint.setChannel(channel);
		endpoint.setMessageClosure(messageClosure);
		endpoint.setResponseEncoder(responseEncoder);
		endpoint.setResponseContext(responseContext);
		
		endpoint.start();
		return endpoint;
	}

	@Override
	public void setCourse(Receiver messageClosure) {
		this.messageClosure = messageClosure;
	}

	@Override
	public void setResponseContext(Holder responseContext) {
		this.responseContext = responseContext;
	}

	private Receiver messageClosure;
	private Holder responseContext;
	
}
