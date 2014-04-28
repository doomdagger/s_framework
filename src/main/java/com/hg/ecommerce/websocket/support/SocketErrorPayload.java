package com.hg.ecommerce.websocket.support;

/**
 * Carry by SocketMessage As An error Payload
 * @author Li He
 *
 */
public class SocketErrorPayload {
	
	private String errorName;
	
	private String errorDescription;

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
	
}
