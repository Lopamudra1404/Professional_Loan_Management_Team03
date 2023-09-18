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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.loan.loanapp.dto.AdminLogin;
import com.loan.loanapp.dto.Login;
import com.loan.loanapp.entity.Admin;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.exception.AdminException;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.DocumentException;
import com.loan.loanapp.exception.LoansException;
import com.loan.loanapp.service.AdminService;

/******************************************************************************
 * @author Shruti Betti 
 * Description AdminController is responsible for managing admin-related operations via RESTful endpoints. It handles customer's
 *             loan approval, login, profile retrieval,and other customer-related actions. It provides endpoints to interact 
 *             with user data in the system.
 * Endpoints: 
 * - PATCH /approve/{loginId}: Approve all pending loans if conditions satisfied. 
 * - POST /admin/login : Authenticate and log in the admin. 
 * - GET /loans/pending-loans: Retrieve all pending. 
 * - GET /loans/approved-loans : Retrieve all approved loans. 
 * - GET /loans: Retrieve all loans with its details.
 * 
 * Version 1.0 
 * Created Date 12-Sept-2023
 ******************************************************************************/

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	AdminService adminService;

	/******************************************************************************
	 * Method -getPendingLoans 
	 * Description -Gets all loans with status as pending with its details. This function is for the admin.
	 * @return List<Loan> -List of all pending loans 
	 * Created by Shruti Betti 
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@GetMapping("/loans/pending-loans")
	public Collection<Loan> getPendingLoans() throws LoansException {
		return this.adminService.getPendingLoans();
	}

	/******************************************************************************
	 * Method -getApprovedLoans 
	 * Description -Gets all loans with status as approve with its details. This function is for the admin.
	 * @return List<Loan> -List of all aproved loans.
	 *  Created by Shruti Betti 
	 *  Created Date 12-Sept-2023
	 ******************************************************************************/

	@GetMapping("/loans/approved-loans")
	public Collection<Loan> getApprovedLoans() throws LoansException {
		return this.adminService.getApprovedLoans();
	}

	/******************************************************************************
	 * Method -getAllLoans 
	 * Description -Gets all loans with its details. This function is for the admin.
	 * @return List<Loan> -List of all loans
	 * Created by Shruti Betti 
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@GetMapping("/loans")
	public Collection<Loan> getAllLoans() throws LoansException {
		return this.adminService.getAllLoans();
	}

	/******************************************************************************
	 * Method -ApproveLoan. 
	 * Description -Approve Customer's loan provided by its loanId.
	 * 
	 * @param LoanId -loanId to be approved or validated.
	 * @return Loan -Returns Loan entity with updated status(approved/rejected).
	 * @throws LoansException, DocumentException, CustomerException -Raised if there
	 *                         are issues with login.
	 * Created by Shruti Betti
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@PatchMapping("/approve/{loanId}")
	public ResponseEntity<?> approveLoan(@PathVariable Integer loanId) {
		try {
			Loan approvedLoan = adminService.approveLoan(loanId);
			return ResponseEntity.ok(approvedLoan); // Return success with the approved loan data
		} catch (LoansException | DocumentException | CustomerException ex) {
			// Handle LoansException and return a bad request response
			return ResponseEntity.badRequest().body(ex.getMessage());
		} catch (Exception ex) {
			// Handle unexpected exceptions and return an internal server error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}

	/******************************************************************************
	 * Method -AdminLogin 
	 * Description -Logs in the admin based on the provided AdminLoginDetailsDto.
	 * @param AdminLoginDetailsDto -The admin data to be validated.
	 * @return Boolean -Returns true or false accordingly.
	 * @throws AdminException -Raised if there are issues with login.
	 * Created by Shruti Betti 
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@PostMapping("/admin/login")
	public ResponseEntity<String> login(@RequestBody AdminLogin adminDetailsDto) {
		try {
			if (this.adminService.validateAdmin(adminDetailsDto)) {
				return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Login successful\"}");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body("{\"message\":\"Login failed: Incorrect Password\"}");
			}
		} catch (AdminException e) {
			if ("Admin not found with this email".equals(e.getMessage())) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("{\"message\":\"Admin not found with this email\"}");
			} else {
				// Handle other exceptions if needed
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("{\"message\":\"An error occurred\"}");
			}
		}
	}

}
