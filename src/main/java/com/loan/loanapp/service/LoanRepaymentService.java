package com.loan.loanapp.service;

import com.loan.loanapp.dto.LoanRepaymentDto;
import com.loan.loanapp.exception.LoanRepaymentException;

public interface LoanRepaymentService {

	void calculateAndStoreLoanRepayment(LoanRepaymentDto repaymentDTO, Integer loanId) throws LoanRepaymentException;

}