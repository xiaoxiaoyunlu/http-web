package com.zj.server.http.resp;

import com.google.gson.annotations.Expose;
import com.zj.server.http.req.AbstractXipSignal;

public class AbstractXipResponse extends AbstractXipSignal implements
		XipResponse {

	public AbstractXipResponse() {
	}

	public static AbstractXipResponse createRespForError(Class<?> clazz,
			int errorCode, String errorMessage) {
		AbstractXipResponse resp;
		try {
			resp = (AbstractXipResponse) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
		resp.setErrorCode(errorCode);
		resp.setErrorMessage(errorMessage);
		return resp;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Expose
	private int errorCode;
	@Expose
	private String errorMessage;
}
