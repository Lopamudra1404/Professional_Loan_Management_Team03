package com.loan.loanapp.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.loan.loanapp.dao.LoansRepository;
import com.loan.loanapp.dto.LoanDto;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.LoansException;
import com.loan.loanapp.service.LoansService;

/******************************************************************************
 * @author Ankita Khese 
 * Description LoansController is responsible for managing loan-related operations. It handles customer's
 *             loan application, loan details retrieval,and other loan-related actions. 
 * 
 * Version 1.0 Created Date 12-Sept-2023
 ******************************************************************************/

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoansController {
	@Autowired
	LoansService loansService;

	@Autowired
	LoansRepository loansRepo;


	/******************************************************************************
	 * Method -addLoanByCustomerId 
	 * Description -When customer apply for the loan, loan EMI will be calculated and stored in loan details.
	 * @param LoanDto,customerId 
	 * @return Loan -LoanEMI will be stored along with loan details.
	 * @throws customerException -Raised if there are issues with adding loan.
	 * Created by Ankita Khese
	 * Created Date 12-Sept-2023
	 ******************************************************************************/
	
	@PostMapping("/customer/loan/{customerId}")
	public ResponseEntity<Loan> addLoanForCustomer(@RequestBody LoanDto loanDto, @PathVariable Integer customerId)
			throws CustomerException {
		Loan loan = loansService.addLoanByCustomerId(loanDto, customerId);
		return new ResponseEntity<>(loan, HttpStatus.CREATED);
	}
	
	/******************************************************************************
	 * Method -getLoanByCustomerId
	 * Description -Retrieves loan details of particular customer by providing its customerId
	 * @param customerId - customerId whose loan has to be retrieved.
	 * @return Loan -LoanEMI will be stored along with loan details.
	 * @throws loansException -Raised if there is no loan for particular customerId.
	 * Created by Ankita Khese
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@GetMapping("customer/loans/{customerId}")
	public ResponseEntity<?> getLoansByCustomerId(@PathVariable Integer customerId) throws LoansException {

		List<Loan> loanList = loansService.getLoanByCustomerId(customerId);
		return ResponseEntity.ok(loanList);

	}

}
