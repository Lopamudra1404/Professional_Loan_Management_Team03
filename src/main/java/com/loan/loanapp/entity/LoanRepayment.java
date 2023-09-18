package com.loan.loanapp.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class LoanRepayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer loanRepaymentId;
	private Integer monthlyAmountRepayed;
	private String repaymentRecipt;
	private LocalDate repaymentDate;
	private Double lateRepaymentPenalty;
	private Boolean loanRepaymentStatus;
	private LocalDate loanRepaymentDueDate;
	@ManyToOne
	@JoinColumn(name = "loan_id")
	private Loan loan;
//	@ManyToOne
//	@JoinColumn(name="loan_id")
//	private LoanAccount loanAccount;

	public LoanRepayment() {
		super();
	}

	public LoanRepayment(Integer loanRepaymentId, Integer monthlyAmountRepayed, String repaymentRecipt,
			LocalDate repaymentDate, Double lateRepaymentPenalty, Boolean loanRepaymentStatus,
			LocalDate loanRepaymentDueDate) {
		super();
		this.loanRepaymentId = loanRepaymentId;
		this.monthlyAmountRepayed = monthlyAmountRepayed;
		this.repaymentRecipt = repaymentRecipt;
		this.repaymentDate = repaymentDate;
		this.lateRepaymentPenalty = lateRepaymentPenalty;
		this.loanRepaymentStatus = loanRepaymentStatus;
		this.loanRepaymentDueDate = loanRepaymentDueDate;

	}

	public Integer getLoanRepaymentId() {
		return loanRepaymentId;
	}

	public void setLoanRepaymentId(Integer loanRepaymentId) {
		this.loanRepaymentId = loanRepaymentId;
	}

	public Integer getMonthlyAmountRepayed() {
		return monthlyAmountRepayed;
	}

	public void setMonthlyAmountRepayed(Integer monthlyAmountRepayed) {
		this.monthlyAmountRepayed = monthlyAmountRepayed;
	}

	public String getRepaymentRecipt() {
		return repaymentRecipt;
	}

	public void setRepaymentRecipt(String repaymentRecipt) {
		this.repaymentRecipt = repaymentRecipt;
	}

	public LocalDate getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(LocalDate repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public Double getLateRepaymentPenalty() {
		return lateRepaymentPenalty;
	}

	public void setLateRepaymentPenalty(Double lateRepaymentPenalty) {
		this.lateRepaymentPenalty = lateRepaymentPenalty;
	}

	public Boolean getLoanRepaymentStatus() {
		return loanRepaymentStatus;
	}

	public void setLoanRepaymentStatus(Boolean loanRepaymentStatus) {
		this.loanRepaymentStatus = loanRepaymentStatus;
	}

	public LocalDate getLoanRepaymentDueDate() {
		return loanRepaymentDueDate;
	}

	public void setLoanRepaymentDueDate(LocalDate loanRepaymentDueDate) {
		this.loanRepaymentDueDate = loanRepaymentDueDate;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

}
