package com.loan.loanapp.service;

import java.util.Collection;

import com.loan.loanapp.dto.BankLogin;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanAccount;
import com.loan.loanapp.entity.LoanDisbursement;
import com.loan.loanapp.exception.BankException;
import com.loan.loanapp.exception.LoanDisbursementException;
import com.loan.loanapp.exception.LoansException;

public interface BankService {

	LoanAccount generateLoanAccount(Integer loanId) throws LoansException;

	LoanDisbursement disburseLoanByLoanId(Integer id) throws LoanDisbursementException, LoansException;

	Collection<LoanDisbursement> getAllDisbursedLoans();

	LoanDisbursement getLoanDisbursementById(Integer id) throws LoanDisbursementException;

	Boolean validateBank(BankLogin bankDetailsDto) throws BankException;

	Collection<Loan> getApprovedLoans() throws LoansException;

	Collection<Loan> getApprovedAndAccountGeneratedLoans() throws LoansException;

	Collection<LoanAccount> getAllAccounts();

}