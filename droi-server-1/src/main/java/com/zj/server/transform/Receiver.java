package com.zj.server.transform;

public interface Receiver {
	/**
	 * 接收请求信息
	 * @param obj
	 */
	void messageReceived(Object obj);

}
