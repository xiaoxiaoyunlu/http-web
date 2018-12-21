package com.zj.server.http.endpoint;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.net.InetSocketAddress;

import lombok.extern.slf4j.Slf4j;

import com.zj.server.common.Holder;
import com.zj.server.common.IpPortPair;
import com.zj.server.http.resp.DefaultHttpResponseSender;
import com.zj.server.http.resp.HttpResponseSender;
import com.zj.server.transform.HttpResponseJSONEncoder;
import com.zj.server.transform.KeyTransformer;
import com.zj.server.transform.Receiver;
import com.zj.server.transform.Transformer;
import com.zj.server.transport.TransportUtil;

@Slf4j
public class ServerEndpoint implements Endpoint {

	@Override
	public void send(Object bean) {
		if(null != bean){
			Object key = this.keyTransformer.transform(bean);
			if(null == key){
				log.error("ServerEndpoint=====Http Request getIdentification is null");
				return;
			}
			if(null == this.responseContext){
				log.error("ServerEndpoint=====responseContext is null");
				return;
			}
			//发送后，删除这个请求的Identitcation 否则会重复处理
			HttpRequest req = (HttpRequest) getResponseContext().getAndRemove(key);
			if(null == req){
				log.error("ServerEndpoint=====Http Request is null");
				return;
			}
			TransportUtil.attachRequest(bean, req);
			doSend(bean);
		}

	}

	@Override
	public void send(Object bean, Receiver receiver) {
		throw new UnsupportedOperationException("not implemented yet!");
	}

	@Override
	public void messageReceived(Object bean) {
		Object key = this.keyTransformer.transform(bean);
		if(null != key){
			getResponseContext().put(key, TransportUtil.getRequestOf(bean));
		}
		if(null != messageClosure){
			messageClosure.messageReceived(bean);
		}

	}

	@Override
	public void stop() {
		this.responseContext = null;
		this.messageClosure = null;
		this.channel = null;

	}

	@Override
	public void start() {

	}

	@Override
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public IpPortPair getRemoteAddress() {
		if(null == addr){
			addr = (InetSocketAddress) channel.remoteAddress();
		}
		return new IpPortPair(addr.getAddress().getHostAddress(),addr.getPort());
	}

	@Override
	public void setAddr(InetSocketAddress inetSocketAddress) {
		this.addr = inetSocketAddress;

	}
	
	private void doSend(Object bean){
		if(null != bean){
            HttpResponse response = responseEncoder.transform(bean);
            httpResponseSender.sendResponse(channel, response);
		}
	}
	
	private Receiver messageClosure;
    private Holder responseContext;
    private KeyTransformer keyTransformer = new KeyTransformer();
    private Channel channel;
    private HttpResponseSender httpResponseSender = new DefaultHttpResponseSender();
    private Transformer<Object,HttpResponse> responseEncoder = new HttpResponseJSONEncoder();
    private InetSocketAddress addr;
	public Receiver getMessageClosure() {
		return messageClosure;
	}

	public void setMessageClosure(Receiver messageClosure) {
		this.messageClosure = messageClosure;
	}

	public Holder getResponseContext() {
		return responseContext;
	}

	public void setResponseContext(Holder responseContext) {
		this.responseContext = responseContext;
	}

	public KeyTransformer getKeyTransformer() {
		return keyTransformer;
	}

	public void setKeyTransformer(KeyTransformer keyTransformer) {
		this.keyTransformer = keyTransformer;
	}

	public HttpResponseSender getHttpResponseSender() {
		return httpResponseSender;
	}

	public void setHttpResponseSender(HttpResponseSender httpResponseSender) {
		this.httpResponseSender = httpResponseSender;
	}

	public Transformer<Object, HttpResponse> getResponseEncoder() {
		return responseEncoder;
	}

	public void setResponseEncoder(Transformer<Object, HttpResponse> responseEncoder) {
		this.responseEncoder = responseEncoder;
	}

	public Channel getChannel() {
		return channel;
	}

	public InetSocketAddress getAddr() {
		return addr;
	}

}
