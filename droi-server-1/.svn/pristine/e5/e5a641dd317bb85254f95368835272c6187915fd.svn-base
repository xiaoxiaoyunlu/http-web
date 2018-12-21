package com.zj.server.transform;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zj.server.common.MsgCode2TypeMetainfo;
import com.zj.server.http.req.XipHeader;
import com.zj.server.http.req.XipMessage;
import com.zj.server.http.req.XipSignal;
import com.zj.server.utils.ByteUtil;
import com.zj.server.utils.DESUtil;
import com.zj.server.utils.ZipUtil;
/**
 * http request 请求解密处理 类
 * @author USER
 *
 */
@Slf4j
public class HttpRequestJSONDecoder implements Transformer<FullHttpRequest, Object> {
	
	
	private MsgCode2TypeMetainfo typeMetaInfo;
	private int dumpBytes;
	private boolean isDebugEnabled;
	private byte[] encryptKey;
	private Gson gson;
	
	public HttpRequestJSONDecoder() {
		this.dumpBytes = 256;

		this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
	}

	@Override
	public Object transform(FullHttpRequest request) {
		ByteBuf content = request.content();
		if(null != content){
			byte[] bytes =  new byte[content.readableBytes()];
			content.readBytes(bytes);
			if(log.isErrorEnabled() && isDebugEnabled){
				log.debug(ByteUtil.bytesAsHexString(bytes,dumpBytes));
			}
			boolean isPress = false;
			if(null != request.headers().get("isPress")){
				isPress = true;
			}
			XipSignal signal = decodeXipSignal(bytes, isPress);
			if ((log.isDebugEnabled()) && (isDebugEnabled)) {
		        log.debug("decoded signal:{}", ToStringBuilder.reflectionToString(signal));
		     }
		     return signal;
		}
		return null;
	}

	private XipSignal decodeXipSignal(byte[] bytes, boolean isPress) {
		byte[] content = bytes;
		if(null != getEncryptKey()){
			try {
				// DES 解密  
				content = DESUtil.decrypt(content, getEncryptKey());
			} catch (Exception e) {
				throw new RuntimeException("decode decryption failed." + e.getMessage());
			}
		}
		if(isPress){
			try {
				// 压缩处理
				content = ZipUtil.compress(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String xipMessgeStr = new String(content,CharsetUtil.UTF_8);
		xipMessgeStr=checkHttpContentJSON(xipMessgeStr);
		XipMessage xipMessage = this.gson.fromJson(xipMessgeStr,XipMessage.class);
		if(null == xipMessage){
			log.warn("invalid xipmessage");
			return null;
		}
		
		XipHeader header = this.gson.fromJson(xipMessage.getXipHeader(), XipHeader.class);
		Class<?> type = this.typeMetaInfo.find(header.getMessageCode());
		if(null == type){
			throw new RuntimeException("unknow message code:" + header.getMessageCode());
		}
		log.info("messagecode-"+header.getMessageCode()+"-request:" + xipMessgeStr);
		 XipSignal signal = (XipSignal) this.gson.fromJson(xipMessage.getXipBody(),type);
		 if(null != signal){
			 signal.setIdentification(header.getTransactionAsUUID());
		 }
		
		return signal;
	}
	
	private String checkHttpContentJSON(String jsonStr){
		if(!StringUtils.isBlank(jsonStr)){
			if(!jsonStr.endsWith("}")){
				int result = jsonStr.lastIndexOf("}");
				return jsonStr.substring(0, result+1);
			}
		}
		return jsonStr;
	} 

	public MsgCode2TypeMetainfo getTypeMetaInfo() {
		return typeMetaInfo;
	}

	public void setTypeMetaInfo(MsgCode2TypeMetainfo typeMetaInfo) {
		this.typeMetaInfo = typeMetaInfo;
	}

	public int getDumpBytes() {
		return dumpBytes;
	}

	public void setDumpBytes(int dumpBytes) {
		this.dumpBytes = dumpBytes;
	}

	public boolean isDebugEnabled() {
		return isDebugEnabled;
	}

	public void setDebugEnabled(boolean isDebugEnabled) {
		this.isDebugEnabled = isDebugEnabled;
	}

	public byte[] getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(byte[] encryptKey) {
		this.encryptKey = encryptKey;
	}

}
