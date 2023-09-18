package com.loan.loanapp.dto;

public class LoanCalculatorDto {
	private double loanAmount;
	private double annualInterestRate;
	private int loanTermInYears;

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public int getLoanTermInYears() {
		return loanTermInYears;
	}

	public void setLoanTermInYears(int loanTermInYears) {
		this.loanTermInYears = loanTermInYears;
	}

}