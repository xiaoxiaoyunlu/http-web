package com.zj.server.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultHolder implements Holder {

	public DefaultHolder() {
	   map = new ConcurrentHashMap<Object, Object>();	
	}
	
	@Override
	public void put(Object obj, Object value) {
       map.put(obj, value);
	}

	@Override
	public Object get(Object obj) {
		return map.get(obj);
	}

	@Override
	public Object getAndRemove(Object obj) {
	    Object object = map.get(obj);
	    map.remove(obj);
		return object;
	}

	@Override
	public void remove(Object obj) {
       map.remove(obj);
	}

	private Map<Object, Object> map;
}
