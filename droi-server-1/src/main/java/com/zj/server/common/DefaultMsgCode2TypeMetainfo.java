package com.zj.server.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultMsgCode2TypeMetainfo implements MsgCode2TypeMetainfo {
	
	private Map<Integer, Class<?>> codes = new ConcurrentHashMap<Integer, Class<?>>();
	
	public void add(int tag,Class<?> clz){
		codes.put(Integer.valueOf(tag),clz);
	}

	@Override
	public Class<?> find(int mcd) {
		return codes.get(Integer.valueOf(mcd));
	}
	
	public Map<Integer,Class<?>> getAllMetainfo(){
		return codes;
	}

}
