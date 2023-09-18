package com.loan.loanapp.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.loanapp.dao.CustomerRepository;
import com.loan.loanapp.dao.LoansRepository;
import com.loan.loanapp.dto.LoanDto;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanAccount;
import com.loan.loanapp.entity.LoanType;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.LoanRepaymentException;
import com.loan.loanapp.exception.LoansException;

/******************************************************************************
 * @author Ankita Khese 
 * Description LoansService is responsible for managing loan-related operations. It handles customer's
 *             loan application, loan details retrieval,and other loan-related actions. 
 * 
 * Version 1.0 Created Date 12-Sept-2023
 ******************************************************************************/

@Service
public class LoansServiceImpl implements LoansService {

	@Autowired
	LoansRepository loansRepo;
	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	LoansRepository loanAccountRepository;

	@Override
	public Collection<Loan> getAllLoans() {

		return this.loansRepo.findAll();
	}

	/******************************************************************************
	 * Method -addLoanByCustomerId 
	 * Description -When customer apply for the loan, loan EMI will be calculated and stored in loan details.
	 * @param LoanDto,customerId 
	 * @return Loan -LoanEMI will be stored along with loan details.
	 * @throws customerException -Raised if there are issues with adding loan.
	 * Created by Ankita Khese
	 * Created Date 12-Sept-2023
	 ******************************************************************************/
	
	@Override
	public Loan addLoanByCustomerId(LoanDto loanDto, Integer customerId) throws CustomerException {

		Double loanAmount = loanDto.getLoanAmount();
		Double rateOfInterest = 12.0;
		Double interestRate = rateOfInterest / 100; // Assuming rate is given in percentage
		Integer loanTenure = loanDto.getLoanTenture();
		LoanType loanType = loanDto.getLoanType();

		Double emi = calculateEMI(loanAmount, interestRate, loanTenure);
		Loan loan = new Loan();
		loan.setLoanType(loanType);
		loan.setLoanAmount(loanAmount);
		loan.setLoanIntrest(interestRate * 100);
		loan.setLoanTenture(loanTenure);
		loan.setLoanEMI(emi);
		Optional<Customer> customerOpt = this.customerRepo.findById(customerId);
		if (!customerOpt.isPresent())
			throw new CustomerException("Customer not present");
		loan = this.loansRepo.save(loan);
		Customer customer = customerOpt.get();
		customer.getLoans().add(loan);
		loan.setCustomer(customer);
		this.customerRepo.save(customer);

		return loan;// this.customerRepo.save(customer);

	}

	public Double calculateEMI(Double loanAmount, Double interestRate, Integer loanTenure) {
		Double r = interestRate / 12; // Monthly interest rate
		Integer n = loanTenure * 12; // Total number of payments

		// Calculate the EMI
		Double emi = (loanAmount * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);

		// Round the EMI to two decimal places
		emi = Math.round(emi * 100.0) / 100.0;

		return emi;
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
	
	@Override
	public List<Loan> getLoanByCustomerId(Integer customerId) throws LoansException {
		Optional<Customer> customerOpt = this.customerRepo.findById(customerId);
		if (!customerOpt.isPresent())
			throw new LoansException("Customer does not exist" + customerId);

		Customer customer = customerOpt.get();
		List<Loan> loanList = customer.getLoans();

		if (loanList.isEmpty())
			throw new LoansException("No loans found for customer with ID" + customerId);

		return loanList;
	}

}
