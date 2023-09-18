package com.loan.loanapp.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;
	private String customerName;
	private String customerEmail;
	private String customerPassword;
	private Long customerPhoneNo;
	private LocalDate customerDob;
	private String customerNationality;
	private String customerGender;
	private Integer customerAge;
	private Integer workexperience;
	private Double customerIncome;
	private Integer customerCreditScore;
	private String address;

	@OneToMany
	private List<Loan> loans = new ArrayList<Loan>();

	@OneToMany
	private List<Document> documents = new ArrayList<Document>();

	@OneToMany
	private List<LoanRepayment> loanrepayment = new ArrayList<LoanRepayment>();

	public Customer() {
		super();
	}

	public Customer(Integer customerId, String customerName, String customerEmail, String customerPassword,
			Long customerPhoneNo, LocalDate customerDob, String customerNationality, String customerGender,
			Integer customerAge, Integer workexperience, Double customerIncome, Integer customerCreditScore,
			String address, List<Loan> loans, List<Document> documents, List<LoanRepayment> loanrepayment) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPassword = customerPassword;
		this.customerPhoneNo = customerPhoneNo;
		this.customerDob = customerDob;
		this.customerNationality = customerNationality;
		this.customerGender = customerGender;
		this.customerAge = customerAge;
		this.workexperience = workexperience;
		this.customerIncome = customerIncome;
		this.customerCreditScore = customerCreditScore;
		this.address = address;
		this.loans = loans;
		this.documents = documents;
		this.loanrepayment = loanrepayment;
	}

	public Customer(Integer customerId, String customerName, String customerEmail, String customerPassword) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPassword = customerPassword;
	}

	public Integer getWorkexperience() {
		return workexperience;
	}

	public void setWorkexperience(Integer workexperience) {
		this.workexperience = workexperience;
	}

	public Double getCustomerIncome() {
		return customerIncome;
	}

	public void setCustomerIncome(Double customerIncome) {
		this.customerIncome = customerIncome;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public Long getCustomerPhoneNo() {
		return customerPhoneNo;
	}

	public void setCustomerPhoneNo(Long customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}

	public LocalDate getCustomerDob() {
		return customerDob;
	}

	public void setCustomerDob(LocalDate customerDob) {
		this.customerDob = customerDob;
	}

	public String getCustomerNationality() {
		return customerNationality;
	}

	public void setCustomerNationality(String customerNationality) {
		this.customerNationality = customerNationality;
	}

	public String getCustomerGender() {
		return customerGender;
	}

	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}

	public Integer getCustomerAge() {
		return customerAge;
	}

	public void setCustomerAge(Integer customerAge) {
		this.customerAge = customerAge;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public List<LoanRepayment> getLoanrepayment() {
		return loanrepayment;
	}

	public void setLoanrepayment(List<LoanRepayment> loanrepayment) {
		this.loanrepayment = loanrepayment;
	}

	public Integer getCustomerCreditScore() {
		return customerCreditScore;
	}

	public void setCustomerCreditScore(Integer customerCreditScore) {
		this.customerCreditScore = customerCreditScore;
	}

}
