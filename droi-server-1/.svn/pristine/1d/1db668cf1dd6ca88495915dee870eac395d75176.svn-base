package com.zj.server.transform;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.zj.server.anno.Compress;
import com.zj.server.anno.SignalCode;
import com.zj.server.http.req.XipHeader;
import com.zj.server.http.req.XipMessage;
import com.zj.server.http.req.XipSignal;
import com.zj.server.transport.TransportUtil;
import com.zj.server.utils.ByteUtil;
import com.zj.server.utils.DESUtil;
import com.zj.server.utils.ZipUtil;

/**
 * http reponse 响应加密处理类
 * 
 * @author USER
 *
 */
@Slf4j
public class HttpResponseJSONEncoder implements
		Transformer<Object, HttpResponse> {

	private int dumpBytes = 256;
	private static final String UUID_STR="uuid";
	private static final String IS_PRESS="isPress";
	private boolean isDebugEnabled;
	private byte[] encryptKey;
	// private Gson gson = new
	// GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	// 重新定义gson
	// excludeFieldsWithoutExposeAnnotation用来使@Expose注解生效
	// disableHtmlEscaping用来告诉Gson不要做html escape，默认情况下Gson会将字符串中的 <、
	// >、=、&等字符做html escape。
	private Gson gson = initGson(); 

//	public HttpResponseJSONEncoder() {
//		initGson();
//	}

	@Override
	public HttpResponse transform(Object signal) {
		
		DefaultFullHttpResponse resp = null;
        
		
		if ((signal instanceof XipSignal)) {
			byte[] bytes = encodeXip((XipSignal) signal, resp);
			if (log.isDebugEnabled()) {
				log.debug("signal as hex:{} \r\n{} ",
						ByteUtil.bytesAsHexString(bytes, this.dumpBytes));
			}
			if (null != bytes) {
				resp = new DefaultFullHttpResponse(
						HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.wrappedBuffer(bytes));
				resp.headers().set(HttpHeaderNames.CONTENT_LENGTH, Integer.valueOf(bytes.length));
			}
		}
		FullHttpRequest req = (FullHttpRequest) TransportUtil.getRequestOf(signal);
		if (req != null) {
			String uuid = req.headers().get(UUID_STR);
			if (uuid != null) {
				resp.headers().set(UUID_STR, uuid);
			}
			String keepAlive = req.headers().get(HttpHeaderNames.CONNECTION);
			if (keepAlive != null) {
				resp.headers().set(HttpHeaderNames.CONNECTION, keepAlive);
			}
		}
		resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/x-tar");
		return resp;
	}

	@SuppressWarnings("finally")
	private byte[] encodeXip(XipSignal signal, DefaultFullHttpResponse resp) {
		SignalCode attr = (SignalCode) signal.getClass().getAnnotation(
				SignalCode.class);
		if (null == attr) {
			throw new RuntimeException(
					"invalid signal, no messageCode defined.");
		}
		Compress press = (Compress) signal.getClass().getAnnotation(
				Compress.class);
		if (press != null) {
			resp.headers().set(IS_PRESS, Boolean.valueOf(true));
		}
		XipHeader header = createHeader((byte) 1, signal.getIdentification(),
				attr.messageCode());

		header.setTypeForClass(signal.getClass());

		XipMessage xipMessage = new XipMessage();
		
		byte[] content = null;
		String respStr=null;
		
		try {
			String body = this.gson.toJson(signal);
			xipMessage.setXipBody(body);
			String head = this.gson.toJson(header);
			xipMessage.setXipHeader(head);
			
			respStr= this.gson.toJson(xipMessage);
			content = respStr.getBytes("utf-8");
			
		} catch (Exception e1) {
			xipMessage.setXipBody("{\"errorCode\":500,\"errorMessage\":\"系统繁忙，请稍后重试.\"}");
			respStr= this.gson.toJson(xipMessage);
			content = respStr.getBytes("utf-8");
			e1.printStackTrace();
			
		}finally{
			log.info("messagecode--------"+header.getMessageCode()+"-response:" + respStr);
		if (press != null) {
			
			try {
				content = ZipUtil.compress(content);
				resp.headers().set(IS_PRESS, Boolean.valueOf(true));
			} catch (IOException e) {
				log.error("err in compress content,e=[{}]", e.getCause());
			}
		}
		if (getEncryptKey() != null) {
			try {
				content = DESUtil.encrypt(content, getEncryptKey());
			} catch (Exception e) {
				throw new RuntimeException("encode decryption failed."
						+ e.getMessage());
			}
		}
		return content;
		}
		
	}

	private XipHeader createHeader(byte basicVer, UUID id, int messageCode) {
		XipHeader header = new XipHeader();
		header.setTransaction(id);
		header.setMessageCode(messageCode);
		header.setBasicVer(basicVer);
		return header;
	}

	public int getDumpBytes() {
		return this.dumpBytes;
	}

	public void setDumpBytes(int dumpBytes) {
		this.dumpBytes = dumpBytes;
	}

	public boolean isDebugEnabled() {
		return this.isDebugEnabled;
	}

	public void setDebugEnabled(boolean isDebugEnabled) {
		this.isDebugEnabled = isDebugEnabled;
	}

	public byte[] getEncryptKey() {
		return this.encryptKey;
	}

	public void setEncryptKey(byte[] encryptKey) {
		this.encryptKey = encryptKey;
	}
	
	public Gson initGson(){
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(Double.class, new TypeAdapter<Double>() {

					@Override
					public void write(JsonWriter out, Double value)
							throws IOException {
						if (null == value) {
							out.nullValue();
							return;
						}
						out.value(new BigDecimal(value).setScale(6,
								BigDecimal.ROUND_HALF_EVEN));

					}

					@Override
					public Double read(JsonReader in) throws IOException {
						if (in.peek() == JsonToken.NULL) {
							in.nextNull();
							return null;
						}

						return in.nextDouble();
					}
				}).disableHtmlEscaping().create();
	}

}
