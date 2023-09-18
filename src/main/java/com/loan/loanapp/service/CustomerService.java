package com.loan.loanapp.service;

import java.util.Collection;

import com.loan.loanapp.dto.LoanCalculatorDto;
import com.loan.loanapp.dto.LoanCalculatorResultDto;
import com.loan.loanapp.dto.Login;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.exception.CustomerException;

public interface CustomerService {

	Customer addCustomer(Customer customer) throws CustomerException;

	String deleteCustomerById(Integer id) throws CustomerException;

	Customer updateCustomer(Customer customer) throws CustomerException;

	Customer getCustomerById(Integer id) throws CustomerException;

	Collection<Customer> getAllCustomers();

	Customer validateCustomer(Login loginDetailsDto) throws CustomerException;
	
	LoanCalculatorResultDto calculateLoan(LoanCalculatorDto loanCalculatorDto);

}
