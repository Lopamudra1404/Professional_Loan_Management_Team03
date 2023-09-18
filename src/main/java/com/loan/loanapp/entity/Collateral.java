package com.loan.loanapp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Collateral {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer collateralId;
	private String collateralType;
	private Double collateralValue;

	@JsonIgnore
	@OneToOne
	private Customer customer;

	@JsonIgnore
	@OneToOne
	private Loan loan;

	public Collateral() {
		super();
	}

	public Collateral(Integer collateralId, String collateralType, Double collateralValue, Customer customer,
			Loan loan) {
		super();
		this.collateralId = collateralId;
		this.collateralType = collateralType;
		this.collateralValue = collateralValue;
		this.customer = customer;
		this.loan = loan;
	}

	public Integer getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(Integer collateralId) {
		this.collateralId = collateralId;
	}

	public String getCollateralType() {
		return collateralType;
	}

	public void setCollateralType(String collateralType) {
		this.collateralType = collateralType;
	}

	public Double getCollateralValue() {
		return collateralValue;
	}

	public void setCollateralValue(Double collateralValue) {
		this.collateralValue = collateralValue;
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

}