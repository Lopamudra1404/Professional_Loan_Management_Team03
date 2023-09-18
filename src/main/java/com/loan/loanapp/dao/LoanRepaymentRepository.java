package com.loan.loanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.loan.loanapp.entity.LoanRepayment;

public interface LoanRepaymentRepository extends JpaRepository<LoanRepayment, Integer> {

	LoanRepayment findTopByLoanLoanIdOrderByRepaymentDateDesc(Integer loanId);

}
