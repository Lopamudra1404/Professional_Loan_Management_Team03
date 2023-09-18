package com.loan.loanapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LoanAccount {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer loanAccountId;
	private String accountNo;
	private Double totalAmount;
	private Double balance;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "customer_id")
	Customer customer;
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "loan_id")
	Loan loan;

	public LoanAccount() {
		super();
	}

	public LoanAccount(Integer loanAccountId, String accountNo, Double totalAmount, Double balance, Customer customer,
			Loan loan) {
		super();
		this.loanAccountId = loanAccountId;
		this.accountNo = accountNo;
		this.totalAmount = totalAmount;
		this.balance = balance;
		this.customer = customer;
		this.loan = loan;
	}

	public Integer getLoanAccountId() {
		return loanAccountId;
	}

	public void setLoanAccountId(Integer loanAccountId) {
		this.loanAccountId = loanAccountId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

}