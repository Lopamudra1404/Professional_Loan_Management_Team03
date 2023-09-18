package com.loan.loanapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Admin {
	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer adminId;
	private String adminName;
	private String password;
	private String adminEmail;
	private Integer adminPhoneNo;

	@JsonIgnore
	@OneToMany
	private List<Loan> loans = new ArrayList<Loan>();
	@JsonIgnore
	@OneToOne
	private Bank bank;

	public Admin() {
		super();
	}

	public Admin(Integer adminId, String adminName, String password, String adminEmail, Integer adminPhoneNo,
			List<Loan> loans, Bank bank) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.password = password;
		this.adminEmail = adminEmail;
		this.adminPhoneNo = adminPhoneNo;
		this.loans = loans;
		this.bank = bank;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public Integer getAdminPhoneNo() {
		return adminPhoneNo;
	}

	public void setAdminPhoneNo(Integer adminPhoneNo) {
		this.adminPhoneNo = adminPhoneNo;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

}