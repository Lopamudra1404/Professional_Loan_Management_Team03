package com.loan.loanapp.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.loan.loanapp.dao.CustomerRepository;
import com.loan.loanapp.dto.LoanCalculatorDto;
import com.loan.loanapp.dto.LoanCalculatorResultDto;
import com.loan.loanapp.dto.Login;

import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.exception.AdminException;
import com.loan.loanapp.exception.CustomerException;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepo;

	@Override
	public Customer addCustomer(Customer customer) throws CustomerException {
		if (customer.getCustomerName() == null || customer.getCustomerName().isEmpty()) {
	        throw new CustomerException("Customer name is missing");
	    }
		if (customer.getCustomerEmail() == null || customer.getCustomerEmail().isEmpty()) {
	        throw new CustomerException("Customer email is missing");
	    }
		if (customer.getCustomerPassword() == null || customer.getCustomerPassword().isEmpty()) {
	        throw new CustomerException("Customer password is missing");
	    }
		Optional<Customer> customerOpt = this.customerRepo.findByCustomerEmail(customer.getCustomerEmail());
		if (customerOpt.isPresent())
			throw new CustomerException("Customer with email " + customer.getCustomerEmail() + " already exists");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(customer.getCustomerPassword());
		customer.setCustomerPassword(encodedPassword);
		return this.customerRepo.save(customer);
	}

	@Override
	public String deleteCustomerById(Integer id) throws CustomerException {
		Optional<Customer> customerOpt = this.customerRepo.findById(id);
		if (!customerOpt.isPresent())
			throw new CustomerException("Customer doesn't exist to delete for id: " + id);
		this.customerRepo.deleteById(id);
		return "Successfully deleted";
	}

	@Override
	public Customer updateCustomer(Customer customer) throws CustomerException {
		Optional<Customer> userOpt = this.customerRepo.findByCustomerEmail(customer.getCustomerEmail());
		if (!userOpt.isPresent())
			throw new CustomerException("Customer not found");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(customer.getCustomerPassword());
		customer.setCustomerPassword(encodedPassword);
		return customerRepo.save(customer);
	}

	@Override
	public Customer getCustomerById(Integer id) throws CustomerException {
		Optional<Customer> customerOpt = this.customerRepo.findById(id);
		if (!customerOpt.isPresent())
			throw new CustomerException("Customer doesn't exist for id: " + id);
		return customerOpt.get();
	}

	@Override
	public Customer validateCustomer(Login loginDetailsDto) throws CustomerException {

		String email1 = loginDetailsDto.getCustomerEmail();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Optional<Customer> opCustomer = customerRepo.findByCustomerEmail(email1);
		if (opCustomer.isPresent()) {
			Customer dbCustomer = opCustomer.get();

			if (passwordEncoder.matches(loginDetailsDto.getCustomerPassword(), dbCustomer.getCustomerPassword())) {
				return dbCustomer;
			} else {
				throw new CustomerException("Invalid Password");
			}
		}
		throw new CustomerException("Customer not found with this email");

	}

	@Override
	public Collection<Customer> getAllCustomers() {

		return this.customerRepo.findAll();
	}

	/******************************************************************************
	 * Method -CalculateLoan 
	 * Description -Customer can check EMI accotdingly using loanEmiCalculator.
	 * @param LoanCalculatorDto -Contains LoanAmount, Interest and Tenure to calculate its EMI.
	 * @return LoanCalculatorResultDto -Returns LoanCalculatorResultDto according to amount, interest and tenure provided.
	 * Created by Shruti Betti 
	 * Created Date 12-Sept-2023
	 ******************************************************************************/
	
	@Override
	public LoanCalculatorResultDto calculateLoan(LoanCalculatorDto loanCalculatorDto) {
		double annualInterestRate = loanCalculatorDto.getAnnualInterestRate() / 100; // Convert to decimal
		double monthlyInterestRate = annualInterestRate / 12;
		int numberOfPayments = loanCalculatorDto.getLoanTermInYears() * 12;

		double monthlyPayment = (loanCalculatorDto.getLoanAmount() * monthlyInterestRate)
				/ (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
		double totalAmountPaid = monthlyPayment * numberOfPayments;
		double totalInterestPaid = totalAmountPaid - loanCalculatorDto.getLoanAmount();

		double roundedMonthlyPayment = Math.round(monthlyPayment * 100.0) / 100.0;
		double roundedTotalAmountPaid = Math.round(totalAmountPaid * 100.0) / 100.0;
		double roundedTotalInterestPaid = Math.round(totalInterestPaid * 100.0) / 100.0;

		return new LoanCalculatorResultDto(roundedMonthlyPayment, roundedTotalAmountPaid, roundedTotalInterestPaid);

	}

}
