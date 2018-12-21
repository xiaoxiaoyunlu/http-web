package com.zj.server.http.endpoint;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;

import com.zj.server.common.IpPortPair;
import com.zj.server.transform.Receiver;
import com.zj.server.transform.Sender;

public interface Endpoint extends Sender,Receiver {

	void stop();
	
	void start();
	
	void setChannel(Channel channel);
	
	IpPortPair getRemoteAddress();
	
	void setAddr(InetSocketAddress inetSocketAddress);
	
}
