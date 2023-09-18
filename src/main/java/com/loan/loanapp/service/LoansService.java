package com.loan.loanapp.service;

import java.util.Collection;
import java.util.List;

import com.loan.loanapp.dto.LoanDto;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.LoansException;

public interface LoansService {

	Double calculateEMI(Double loanAmount, Double interestRate, Integer loanTenure);

	Loan addLoanByCustomerId(LoanDto loanDto, Integer customerId) throws CustomerException;

	List<Loan> getLoanByCustomerId(Integer customerId) throws LoansException;

	Collection<Loan> getAllLoans();

}
