package com.zj.server.http.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import java.io.IOException;
import java.net.InetSocketAddress;
import lombok.extern.slf4j.Slf4j;
import com.zj.server.http.endpoint.Endpoint;
import com.zj.server.http.endpoint.EndpointFactory;
import com.zj.server.http.resp.ConstantResponse;
import com.zj.server.http.resp.ConstantResponseReactor;
import com.zj.server.http.resp.HttpReactor;
import com.zj.server.transform.Transformer;
import com.zj.server.transport.TransportUtil;

@Slf4j
public class HttpRequestHandler extends ChannelInboundHandlerAdapter{
	
	private HttpReactor errorReactor = new ConstantResponseReactor(ConstantResponse.RESPONSE_400_NOBODY);
	private Transformer<FullHttpRequest, Object> requestDecoder;
	private Transformer<Object, HttpResponse> responseEncoder;
	private EndpointFactory endpointFactory ;
	
	public HttpRequestHandler(EndpointFactory endpointFactory,
			Transformer<FullHttpRequest, Object> requestDecoder,
			Transformer<Object, HttpResponse> responseEncoder) {
		this.endpointFactory= endpointFactory;
		this.requestDecoder = requestDecoder;
		this.responseEncoder = responseEncoder;
	}

		/**
		 * 等价于 netty 3 的  ChannelOpen ChannelBound 和ChannelConnected 
		 */
		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			
			Channel channel = ctx.channel();
			if (log.isDebugEnabled()) {
				log.debug(Thread.currentThread().getName()+"================channelOpen: ["
						+ channel.remoteAddress() + "]");
			}
			
			Endpoint endpoint = endpointFactory.createEndpoint(channel, responseEncoder);
		    if(null != endpoint){
		    	endpoint.setAddr((InetSocketAddress) channel.remoteAddress());
		    	TransportUtil.attachEndpointToSession(channel, endpoint);
		    }
		}

		/**
		 * netty3的messageReceived 没有了 
		 */
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
				throws Exception {
			Channel ch = ctx.channel();
			if (log.isTraceEnabled()) {
				log.info("message received {}", msg.toString());
			}
			
			if(!(msg instanceof FullHttpRequest)){
				errorReactor.onHttpRequest(ch, null);
				return ;
			}
			FullHttpRequest request = (FullHttpRequest)msg;
			Object signal = requestDecoder.transform(request);
			if (null != signal) {
				Endpoint endpoint = TransportUtil.getEndpointOfSession(ch);
				if (null != endpoint) {
					TransportUtil.attachSender(signal, endpoint);
					TransportUtil.attachRequest(signal, request);
					endpoint.messageReceived(signal);
				} else {
					log.warn("missing endpoint, ignore incoming msg:",signal);
				}
			} else if (null != errorReactor) {
				log.error("content is null, try send back client empty HttpResponse.");
				errorReactor.onHttpRequest(null, request);
			} else {
				log.warn("Can not transform bean for req [" + request + "], and missing errorHandler.");
			}
		}
		
		/**
		 * 等价于 netty3 的  ChannelDisconnected ChannelUnbound ChannlClosed
		 */
		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			if (log.isDebugEnabled()) {
				log.debug(Thread.currentThread().getName()+"================channelClosed: ["
						+ ctx.channel().remoteAddress() + "]");
			}
			Endpoint endpoint = TransportUtil.getEndpointOfSession(ctx.channel());
			if (null != endpoint) {
				endpoint.stop();
			}
			// channel 都关闭了， 所以不用去处理
//			TransportUtil.detachEndpointToSession(ctx.channel());
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			cause.printStackTrace();
			if(!(cause.getCause() instanceof IOException)){
				log.error("exceptionCaught: ", cause.getCause());
			}
			ctx.close();
		}
		
	}