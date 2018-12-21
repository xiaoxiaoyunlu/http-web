package com.zj.server.transform;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpRequestJSONEncoder extends MessageToMessageEncoder<FullHttpRequest> {

	@Override
	protected void encode(ChannelHandlerContext ctx, FullHttpRequest msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
