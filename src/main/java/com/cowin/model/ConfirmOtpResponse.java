package com.cowin.model;

public class ConfirmOtpResponse {
	private String token;
	
	private String isNewAccount;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIsNewAccount() {
		return isNewAccount;
	}

	public void setIsNewAccount(String isNewAccount) {
		this.isNewAccount = isNewAccount;
	}
}
