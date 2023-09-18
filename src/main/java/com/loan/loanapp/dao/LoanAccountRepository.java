package com.loan.loanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanAccount;

public interface LoanAccountRepository extends JpaRepository<LoanAccount, Integer> {

	LoanAccount findByLoan_loanId(Integer loanId);

	LoanAccount findByLoan(Loan loan);

}