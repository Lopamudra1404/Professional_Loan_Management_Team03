package com.loan.loanapp.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.loan.loanapp.dto.LoanCalculatorDto;
import com.loan.loanapp.dto.LoanCalculatorResultDto;
import com.loan.loanapp.dto.Login;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.LoansException;
import com.loan.loanapp.service.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping("/customer/registration")
	public Customer Register(@RequestBody Customer customer) throws CustomerException {
		return this.customerService.addCustomer(customer);
	}

	@PutMapping("/customer/update-profile")
	public ResponseEntity<?> updateCustomerProfile(@RequestBody Customer customer) {
		try {
			Customer updatedCustomer = this.customerService.updateCustomer(customer);
			return ResponseEntity.ok(updatedCustomer);
		} catch (CustomerException e) {
			// Handle the exception appropriately, e.g., return an error response
			// or log the error message.
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found: " + e.getMessage());
		} catch (Exception e) {
			// Handle other exceptions if needed.
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Internal server error: " + e.getMessage());
		}
	}

	@GetMapping("/customer/display/profile/{id}")
	public Customer getCustomerById(@PathVariable("id") Integer id) throws CustomerException {
		return this.customerService.getCustomerById(id);
	}

	@GetMapping("/customers")
	public Collection<Customer> getAllLoans() throws LoansException {
		return this.customerService.getAllCustomers();
	}

	// Customer login
	@PostMapping("/customer/login")
	public ResponseEntity<Customer> login(@RequestBody Login loginDetailsDto) {
		try {
			Customer validatedCustomer = this.customerService.validateCustomer(loginDetailsDto);

			if (validatedCustomer != null) {
				return ResponseEntity.status(HttpStatus.OK).body(validatedCustomer);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
			}
		} catch (CustomerException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping("/customer/loan-calculator")
	public LoanCalculatorResultDto calculateLoan(@RequestBody LoanCalculatorDto loanCalculatorDto) {
		return customerService.calculateLoan(loanCalculatorDto);
	}

}
