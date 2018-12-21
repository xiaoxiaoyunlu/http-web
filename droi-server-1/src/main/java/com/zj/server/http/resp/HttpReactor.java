package com.zj.server.http.resp;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;

public interface HttpReactor {
	/**
	 * 处理返回的响应
	 * @param channel
	 * @param httprequest
	 */
	void onHttpRequest(Channel channel, HttpRequest httprequest);

}
