package com.zj.server.http.req;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.zj.server.http.resp.XipResponse;

public class XipHeader {
	public static final int HEADER_LENGTH = 28;
	public static final int XIP_REQUEST = 1;
	public static final int XIP_RESPONSE = 2;
	public static final int XIP_NOTIFY = 3;
	@Expose
	@SerializedName("ver")
	private byte basicVer = 1;
	private int length = 0;
	@Expose
	@SerializedName("type")
	private byte type = 1;
	private short reserved = 0;
	@Expose
	@SerializedName("msb")
	private long firstTransaction;
	@Expose
	@SerializedName("lsb")
	private long secondTransaction;
	@Expose
	@SerializedName("mcd")
	private int messageCode;

	public byte getBasicVer() {
		return this.basicVer;
	}

	public void setBasicVer(byte basicVer) {
		this.basicVer = basicVer;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public byte getType() {
		return this.type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public short getReserved() {
		return this.reserved;
	}

	public void setReserved(short reserved) {
		this.reserved = reserved;
	}

	public long getFirstTransaction() {
		return this.firstTransaction;
	}

	public void setFirstTransaction(long firstTransaction) {
		this.firstTransaction = firstTransaction;
	}

	public long getSecondTransaction() {
		return this.secondTransaction;
	}

	public void setSecondTransaction(long secondTransaction) {
		this.secondTransaction = secondTransaction;
	}

	public int getMessageCode() {
		return this.messageCode;
	}

	public void setMessageCode(int messageCode) {
		if (messageCode <= 0) {
			throw new RuntimeException("invalid message code.");
		}
		this.messageCode = messageCode;
	}

	public void setTransaction(UUID uuid) {
		this.firstTransaction = uuid.getMostSignificantBits();
		this.secondTransaction = uuid.getLeastSignificantBits();
	}

	public UUID getTransactionAsUUID() {
		return new UUID(this.firstTransaction, this.secondTransaction);
	}

	public void setTypeForClass(Class<?> cls) {
		if (XipRequest.class.isAssignableFrom(cls)) {
			this.type = 1;
		} else if (XipResponse.class.isAssignableFrom(cls)) {
			this.type = 2;
		} else if (XipNotify.class.isAssignableFrom(cls)) {
			this.type = 3;
		}
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public static void main(String[] args) {
		XipHeader header = new XipHeader();
		header.setBasicVer((byte) 1);
		header.setLength(131);
		header.setType((byte) 1);
		header.setReserved((short) 0);
		header.setFirstTransaction(6450626838123529635L);
		header.setSecondTransaction(-4941818330698730257L);
		header.setMessageCode(102001);

//		TLVEncoderProvider tlvEncoderProvider = TLVCodecProviders
//				.newBigEndianTLVEncoderProvider();
//		TLVEncoder<Object> tlvObjectEncoder = tlvEncoderProvider
//				.getObjectEncoder();
//
//		List<byte[]> byteList = (List) tlvObjectEncoder.codec(header, null);
//		byte[] content = ByteUtils.union(byteList);
//		System.out.println(content.length);
//		System.out.println(ByteUtil.bytesAsHexString(content, 256));
	}
}
