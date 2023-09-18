package com.loan.loanapp.dto;

public class BankLogin {
	private String bankEmail;
	private String bankPassword;

	public BankLogin(String bankEmail, String bankPassword) {
		super();
		this.bankEmail = bankEmail;
		this.bankPassword = bankPassword;
	}

	public String getBankEmail() {
		return bankEmail;
	}

	public void setBankEmail(String bankEmail) {
		this.bankEmail = bankEmail;
	}

	public String getBankPassword() {
		return bankPassword;
	}

	public void setBankPassword(String bankPassword) {
		this.bankPassword = bankPassword;
	}

}
