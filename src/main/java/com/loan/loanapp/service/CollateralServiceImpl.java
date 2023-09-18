package com.loan.loanapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.loanapp.dao.CollateralRepository;
import com.loan.loanapp.dao.CustomerRepository;
import com.loan.loanapp.dao.LoansRepository;
import com.loan.loanapp.entity.Collateral;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.exception.CollateralException;

@Service
public class CollateralServiceImpl implements CollateralService {

	@Autowired
	CollateralRepository collateralRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	LoansRepository loansRepo;

	@Override
	public Collateral addCollateral(Collateral newCollateral, Integer loanId) throws CollateralException {
		Optional<Loan> loanOpt = this.loansRepo.findById(loanId);

		if (!loanOpt.isPresent()) {
			throw new CollateralException("Loan not found with ID: " + loanId);
		}

		Loan loan = loanOpt.get();
		Integer customerId = loan.getCustomer().getCustomerId(); // Assuming you have a method to retrieve the customer
																	// ID from the loan

		Optional<Customer> customerOpt = this.customerRepo.findById(customerId);

		if (!customerOpt.isPresent()) {
			throw new CollateralException("Customer not found with ID: " + customerId);
		}

		Customer customer = customerOpt.get();

		Optional<Collateral> collateralOpt = this.collateralRepo.findByLoan_loanId(loanId);

		if (collateralOpt.isPresent()) {
			throw new CollateralException("Collateral already added");
		}

		Double salary = customer.getCustomerIncome();
		Double amount = loan.getLoanAmount();

		if (amount > (salary * 2)) {
			// Set the customer for the collateral

			newCollateral.setCustomer(customer);
			newCollateral.setLoan(loan);
			this.collateralRepo.save(newCollateral);

			return newCollateral;
		}

		throw new CollateralException("Collateral not required");
	}

}