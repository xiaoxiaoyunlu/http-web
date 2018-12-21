package com.zj.server.exception;

public class LogicException extends RuntimeException {
	
	private static final long serialVersionUID = -6431685852531481564L;
	private int errorCode;

	public LogicException(int errorCode,String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
