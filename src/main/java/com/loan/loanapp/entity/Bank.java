package com.loan.loanapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Bank {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bankId;
	private String bankName;
	private String bankEmail;
	private String bankPassword;
	private Integer bankCode;
	private String bankAddress;
	private Integer bankPhoneNumber;

	@OneToMany
	private List<LoanDisbursement> loanDisbursement = new ArrayList<LoanDisbursement>();
	@OneToMany
	private List<Loan> loans = new ArrayList<Loan>();

	public Bank() {
		super();
	}

	public Bank(Integer bankId, String bankName, String bankEmail, String bankPassword, Integer bankCode,
			String bankAddress, Integer bankPhoneNumber, List<LoanDisbursement> loanDisbursement, List<Loan> loans) {
		super();
		this.bankId = bankId;
		this.bankName = bankName;
		this.bankEmail = bankEmail;
		this.bankPassword = bankPassword;
		this.bankCode = bankCode;
		this.bankAddress = bankAddress;
		this.bankPhoneNumber = bankPhoneNumber;
		this.loanDisbursement = loanDisbursement;
		this.loans = loans;
	}

	public Bank(Integer bankId, String bankName, String bankEmail, String bankPassword, Integer bankCode,
			String bankAddress, Integer bankPhoneNumber) {
		super();
		this.bankId = bankId;
		this.bankName = bankName;
		this.bankEmail = bankEmail;
		this.bankPassword = bankPassword;
		this.bankCode = bankCode;
		this.bankAddress = bankAddress;
		this.bankPhoneNumber = bankPhoneNumber;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public Integer getBankCode() {
		return bankCode;
	}

	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Integer getBankPhoneNumber() {
		return bankPhoneNumber;
	}

	public void setBankPhoneNumber(Integer bankPhoneNumber) {
		this.bankPhoneNumber = bankPhoneNumber;
	}

	public List<LoanDisbursement> getLoanDisbursement() {
		return loanDisbursement;
	}

	public void setLoanDisbursement(List<LoanDisbursement> loanDisbursement) {
		this.loanDisbursement = loanDisbursement;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

}