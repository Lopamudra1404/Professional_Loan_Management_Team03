package com.loan.loanapp.dto;

public class Login {
	private String customerEmail;
	private String customerPassword;

	public Login(String customerEmail, String customerPassword) {
		super();
		this.customerEmail = customerEmail;
		this.customerPassword = customerPassword;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

}
