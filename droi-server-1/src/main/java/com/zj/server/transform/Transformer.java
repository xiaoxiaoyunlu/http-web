package com.zj.server.transform;

@SuppressWarnings("hiding")
public interface Transformer<Object,T> {
	
	T transform(Object obj);

}
