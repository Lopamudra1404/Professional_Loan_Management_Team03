package com.loan.loanapp.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.loan.loanapp.exception.AddressException;
import com.loan.loanapp.exception.AdminException;
import com.loan.loanapp.exception.AppliedLoansException;
import com.loan.loanapp.exception.BankException;
import com.loan.loanapp.exception.CollateralException;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.DocumentException;
import com.loan.loanapp.exception.LoanDisbursementException;
import com.loan.loanapp.exception.LoanRepaymentException;
import com.loan.loanapp.exception.LoansException;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(CustomerException.class)
	public ResponseEntity<String> handleCustomerException(CustomerException customerException) {
		return new ResponseEntity<String>(customerException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AdminException.class)
	public ResponseEntity<String> handleAdminException(AdminException adminException) {
		return new ResponseEntity<String>(adminException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AppliedLoansException.class)
	public ResponseEntity<String> handleAppliedLoansException(AppliedLoansException appliedLoanException) {
		return new ResponseEntity<String>(appliedLoanException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BankException.class)
	public ResponseEntity<String> handleResponseException(BankException bankException) {
		return new ResponseEntity<String>(bankException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DocumentException.class)
	public ResponseEntity<String> DocumentException(DocumentException documentException) {
		return new ResponseEntity<String>(documentException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoanDisbursementException.class)
	public ResponseEntity<String> LoanDisbursementException(LoanDisbursementException loanDisbursementException) {
		return new ResponseEntity<String>(loanDisbursementException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoanRepaymentException.class)
	public ResponseEntity<String> LoanRepaymentException(LoanRepaymentException loanRepaymentException) {
		return new ResponseEntity<String>(loanRepaymentException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoansException.class)
	public ResponseEntity<String> LoansException(LoansException loansException) {
		return new ResponseEntity<String>(loansException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AddressException.class)
	public ResponseEntity<String> AddressException(AddressException addressException) {
		return new ResponseEntity<String>(addressException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CollateralException.class)
	public ResponseEntity<String> CollateralException(CollateralException collateralException) {
		return new ResponseEntity<String>(collateralException.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
