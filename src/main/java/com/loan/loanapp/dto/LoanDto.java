package com.loan.loanapp.dto;

import com.loan.loanapp.entity.LoanType;

public class LoanDto {
	private LoanType loanType;
	private Double loanAmount;
	private Integer loanTenture;

	public LoanDto(LoanType loanType, Double loanAmount, Integer loanTenture) {
		super();
		this.loanType = loanType;
		this.loanAmount = loanAmount;
		this.loanTenture = loanTenture;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Integer getLoanTenture() {
		return loanTenture;
	}

	public void setLoanTenture(Integer loanTenture) {
		this.loanTenture = loanTenture;
	}
}
