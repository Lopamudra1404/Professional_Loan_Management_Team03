package com.loan.loanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.loan.loanapp.dto.LoanRepaymentDto;
import com.loan.loanapp.exception.LoanRepaymentException;
import com.loan.loanapp.service.LoanRepaymentService;

/******************************************************************************
 * @author Rana Shaikh
 * Description LoanRepaymentService is responsible for managing repayments-related operations. It handles customer's
 *             repayments, due date management,as well as generated unique receipt numbers for each repayment on the monthly basis.
 * Endpoints: - PATCH /loan/repayment/{loanAccountId}: Repayment of loan on monthly basis. 
 * Version 1.0 
 * Created Date 12-Sept-2023
 ******************************************************************************/

@RestController
public class LoanRepaymentController {
	@Autowired
	LoanRepaymentService loanRepaymentService;
	
	/******************************************************************************
	 * Method -calculateAndStoreLoanRepayment 
	 * Description -When customer repay the loan EMI it will be calculated and stored and balance from loan account will be deducted.
	 * @param LoanRepaymentDto, LoanAccountId 
	 * @return void -LoanREpayment and loanAccount is been saved.
	 * @throws LoanRepaymentException -Raised if there are issues with repayments.
	 * Created by Rana Shaikh
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@PostMapping("/loan/repayment/{loanAccountId}")
	public ResponseEntity<String> calculateAndStoreLoanRepayment(@RequestBody LoanRepaymentDto loanRepaymentDto,
			@PathVariable("loanAccountId") Integer loanAccountId) throws LoanRepaymentException {

		// Call the service to calculate and store the loan repayment
		loanRepaymentService.calculateAndStoreLoanRepayment(loanRepaymentDto, loanAccountId);
		return ResponseEntity.ok("Loan repayment calculated and stored successfully.");

	}

}
