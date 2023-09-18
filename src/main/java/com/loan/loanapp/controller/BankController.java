package com.loan.loanapp.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanAccount;
import com.loan.loanapp.entity.LoanDisbursement;
import com.loan.loanapp.exception.LoanDisbursementException;
import com.loan.loanapp.exception.LoansException;
import com.loan.loanapp.service.BankService;

/******************************************************************************

* @author Rana Shaikh and Shruti Betti
 
* Description BankService is responsible for managing bank-related operations via RESTful endpoints. It handles customer's
 
  account generation,loan disbursement,view the disbursed loans,view the loan accounts. It provides endpoints to interact with user data in the system.
 
* Version 1.0 Created Date 12-Sept-2023
 
******************************************************************************/

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BankController {
	@Autowired
	BankService bankService;
	
    /******************************************************************************
	 
     * Method -generateLoanAccount.
 
     * Description -Create Customer's loan account for the loanId.
    
      * @param LoanId -loan account is created for the loan Id.
 
     * @return LoanAccount -Returns LoanAccount entity with updated status(account generated).
 
     * @throws LoansException-Raised if the loan account already exist.
 
     * Created by Shruti Betti
 
     * Created Date 12-Sept-2023
 
     ******************************************************************************/

	@PostMapping("/customer/account/{loanId}")
	public ResponseEntity<?> generateLoanAccount(@PathVariable Integer loanId) {
		try {
			LoanAccount loanAccount = bankService.generateLoanAccount(loanId);
			return ResponseEntity.ok().body("{\"message\": \"Loan account generated successfully\"}");
		} catch (LoansException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}

    /******************************************************************************
	 
    * Method - getApprovedAndAccountGeneratedLoans

    * Description -Gets all loans with status as approve and account generated with its details. This function is for the bank.

    * @return List<Loan> -List of all aproved and account generated loans for disbursing the loan.

    *  Created by Rana Shaikh

    *  Created Date 12-Sept-2023

    ******************************************************************************/
	
	@GetMapping("/loans/approved-and-account-generated-loans")
	public ResponseEntity<Collection<Loan>> getApprovedAndAccountGeneratedLoans() {
		try {
			Collection<Loan> loans = bankService.getApprovedAndAccountGeneratedLoans();
			return ResponseEntity.ok(loans);
		} catch (LoansException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle the exception as needed
		}
	}

	 /******************************************************************************
	 
     * Method -disburseLoanByLoanId.
 
     * Description -disburse the loan amount to customer's loan account for the loanId.
    
      * @param LoanId -loan amount is disbursed for the loan Id.
 
     * @return LoanDisbursement -Returns LoanDisbursement entity with updated status(disbursed).
 
     * @throws LoansException,LoanDisbursementException-Raised if the loan is not found,loan account is not created for the loan,loan is already disbursed
 
     * Created by Rana Shaikh
 
     * Created Date 12-Sept-2023
 
     ******************************************************************************/

	@PatchMapping("/loan/disburse-Loan/{loanId}")
	public ResponseEntity<?> disburseLoanByLoanId(@PathVariable Integer loanId)
			throws LoanDisbursementException, LoansException {

		try {
			LoanDisbursement loanDisbursement = bankService.disburseLoanByLoanId(loanId);
			return ResponseEntity.ok().body("{\"message\": \"Loan disbursed successfully\"}");
		} catch (LoanDisbursementException | LoansException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}

	}
}