package com.loan.loanapp.dto;

public class AdminLogin {
	private String adminEmail;
	private String password;

	public AdminLogin(String adminEmail, String password) {
		super();
		this.adminEmail = adminEmail;
		this.password = password;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}