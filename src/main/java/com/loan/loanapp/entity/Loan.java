package com.loan.loanapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer loanId;
	private LoanType loanType;
	private String loanName;
	private Double loanAmount;
	private Double loanIntrest;
	private Integer loanTenture;
	private Double loanEMI;
	private String loanDescription;
	private String status;

	@OneToOne
	@JoinColumn(name = "loanDisbursementId")
	LoanDisbursement loanDisbursement;

	@OneToMany
	private List<Document> document = new ArrayList<Document>();

	@JsonIgnore
	@ManyToOne
	Customer customer;

	@OneToOne
	LoanAccount loanAccount;

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getLoanIntrest() {
		return loanIntrest;
	}

	public void setLoanIntrest(Double loanIntrest) {
		this.loanIntrest = loanIntrest;
	}

	public Integer getLoanTenture() {
		return loanTenture;
	}

	public void setLoanTenture(Integer loanTenture) {
		this.loanTenture = loanTenture;
	}

	public Double getLoanEMI() {
		return loanEMI;
	}

	public void setLoanEMI(Double loanEMI) {
		this.loanEMI = loanEMI;
	}

	public String getLoanDescription() {
		return loanDescription;
	}

	public void setLoanDescription(String loanDescription) {
		this.loanDescription = loanDescription;
	}

	public List<Document> getDocument() {
		return document;
	}

	public void setDocument(List<Document> document) {
		this.document = document;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setLoanDisbursement(LoanDisbursement loanDisbursement) {
		this.loanDisbursement = loanDisbursement;
	}

	public LoanDisbursement getLoanDisbursement() {
		return loanDisbursement;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LoanAccount getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(LoanAccount loanAccount) {
		this.loanAccount = loanAccount;
	}

	public Loan(Integer loanId, LoanType loanType, String loanName, Double loanAmount, Double loanIntrest,
			Integer loanTenture, Double loanEMI, String loanDescription, String status,
			LoanDisbursement loanDisbursement, List<Document> document, Customer customer, LoanAccount loanAccount) {
		super();
		this.loanId = loanId;
		this.loanType = loanType;
		this.loanName = loanName;
		this.loanAmount = loanAmount;
		this.loanIntrest = loanIntrest;
		this.loanTenture = loanTenture;
		this.loanEMI = loanEMI;
		this.loanDescription = loanDescription;
		this.status = status;
		this.loanDisbursement = loanDisbursement;
		this.document = document;
		this.customer = customer;
		this.loanAccount = loanAccount;
	}

	public Loan() {
		super();
		this.status = "pending";
	}

}