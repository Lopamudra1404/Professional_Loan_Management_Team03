package com.loan.loanapp.service;

import java.util.Collection;

import com.loan.loanapp.dto.AdminLogin;
import com.loan.loanapp.entity.Admin;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.exception.AdminException;
import com.loan.loanapp.exception.AppliedLoansException;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.DocumentException;
import com.loan.loanapp.exception.LoansException;

public interface AdminService {

	Loan approveLoan(Integer loanId) throws LoansException, DocumentException, CustomerException;

	Collection<Loan> getPendingLoans() throws LoansException;

	Collection<Loan> getApprovedLoans() throws LoansException;

	Collection<Loan> getAllLoans() throws LoansException;

	Boolean validateAdmin(AdminLogin adminDetailsDto) throws AdminException;

}