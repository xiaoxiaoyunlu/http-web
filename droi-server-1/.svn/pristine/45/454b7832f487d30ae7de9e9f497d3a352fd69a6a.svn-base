package com.zj.server.http.endpoint;

import com.zj.server.common.Holder;
import com.zj.server.transform.Receiver;
import com.zj.server.transform.Transformer;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpResponse;

public interface EndpointFactory {

	Endpoint createEndpoint(Channel channel,Transformer<Object,HttpResponse> transformer);
	
	void setCourse(Receiver receiver);
	
	void setResponseContext(Holder holder);
	
}
