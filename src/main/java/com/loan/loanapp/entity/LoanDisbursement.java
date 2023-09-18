package com.loan.loanapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LoanDisbursement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer loanDisbursementId;
	private Double loanDisbursementAmount;
	private String loanDisbursementDescription;
	private String loanDisbursementStatus;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "loanId")
	Loan loan;

	@OneToMany
	private List<LoanRepayment> loanRepayment = new ArrayList<>();

	public LoanDisbursement() {
		super();

	}

	public LoanDisbursement(Integer loanDisbursementId, Double loanDisbursementAmount,
			String loanDisbursementDescription, String loanDisbursementStatus, Loan loan,
			List<LoanRepayment> loanRepayment) {
		super();
		this.loanDisbursementId = loanDisbursementId;
		this.loanDisbursementAmount = loanDisbursementAmount;
		this.loanDisbursementDescription = loanDisbursementDescription;
		this.loanDisbursementStatus = loanDisbursementStatus;

		this.loan = loan;
		this.loanRepayment = loanRepayment;
	}

	public Integer getLoanDisbursementId() {
		return loanDisbursementId;
	}

	public void setLoanDisbursementId(Integer loanDisbursementId) {
		this.loanDisbursementId = loanDisbursementId;
	}

	public String getLoanDisbursementDescription() {
		return loanDisbursementDescription;
	}

	public void setLoanDisbursementDescription(String loanDisbursementDescription) {
		this.loanDisbursementDescription = loanDisbursementDescription;
	}

	public String getLoanDisbursementStatus() {
		return loanDisbursementStatus;
	}

	public void setLoanDisbursementStatus(String loanDisbursementStatus) {
		this.loanDisbursementStatus = loanDisbursementStatus;
	}

	public List<LoanRepayment> getLoanRepayment() {
		return loanRepayment;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public void setLoanRepayment(List<LoanRepayment> loanRepayment) {
		this.loanRepayment = loanRepayment;
	}

	public void setLoanDisbursementAmount(Double loanDisbursementAmount) {
		this.loanDisbursementAmount = loanDisbursementAmount;
	}

	public Double getLoanDisbursementAmount() {
		return loanDisbursementAmount;
	}

}