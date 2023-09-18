package com.loan.loanapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.loan.loanapp.dao.AdminRepository;
import com.loan.loanapp.dao.CollateralRepository;
import com.loan.loanapp.dao.CustomerRepository;
import com.loan.loanapp.dao.DocumentsRepository;
import com.loan.loanapp.dao.LoansRepository;
import com.loan.loanapp.dto.AdminLogin;
import com.loan.loanapp.entity.Admin;
import com.loan.loanapp.entity.Collateral;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.Document;

import com.loan.loanapp.entity.DocumentType;
import com.loan.loanapp.exception.AdminException;
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.DocumentException;
import com.loan.loanapp.exception.LoansException;

/******************************************************************************
 * @author Shruti Betti 
 * Description AdminService is responsible for managing admin-related operations. It handles customer's
 *             loan approval, login, profile retrieval,and other customer-related actions. 
 * 
 * Version 1.0 
 * Created Date 12-Sept-2023
 ******************************************************************************/

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	LoansRepository loansRepo;

	@Autowired
	DocumentsRepository documentsRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	CollateralRepository collateralRepo;

	@Autowired
	AdminRepository adminRepo;

	@Autowired
	MailService mailService;

	/******************************************************************************
	 * Method -approveLoan. 
	 * Description -Approve Customer's loan provided by its loanId.
	 * @param LoanId -loanId to be approved or validated.
	 * @return Loan -Returns Loan entity with updated status(approved/rejected).
	 * @throws LoansException, DocumentException, CustomerException -Raised if there
	 *                         are issues with login.
	 * Created by Shruti Betti
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@Override
	public Loan approveLoan(Integer loanId) throws LoansException, DocumentException, CustomerException {
		// Retrieve the Loan object using loanId
		Optional<Loan> loanOpt = this.loansRepo.findById(loanId);

		if (!loanOpt.isPresent()) {
			throw new LoansException("Loan does not exist");
		}

		Loan loan = loanOpt.get();

		// Retrieve the associated customer from the loan object
		Customer customer = loan.getCustomer();

		// Check if the loan amount is greater than double the customer's salary
		if (loan.getLoanAmount() > (2 * customer.getCustomerIncome())) {
			// The loan amount is greater than double the salary, so check for collateral
			Optional<Collateral> collateralOpt = this.collateralRepo.findByLoan_loanId(loanId);

			if (!collateralOpt.isPresent()) {
				// No collateral provided, reject the loan
				loan.setStatus("Rejected");
			} else {
				// Collateral is provided; check if it's valid
				boolean isValidCollateral = isValidCollateral(collateralOpt.get().getCollateralValue(),
						customer.getCustomerIncome(), loan.getLoanAmount());

				if (isValidCollateral && areValidDocuments(customer) && isValidCreditScore(customer)) {
					loan.setStatus("Approved");

					// Send an email notification to the customer
					String subject = "Loan Approval Notification";
					String message = "Your loan has been approved. Congratulations!";
					mailService.sendMail(customer.getCustomerEmail(), subject, message);
				} else {
					loan.setStatus("Rejected");
				}
			}
		} else {
			// The loan amount is not greater than double the salary; no collateral required
			if (areValidDocuments(customer) && isValidCreditScore(customer)) {
				loan.setStatus("Approved");

				// Send an email notification to the customer
				String subject = "Loan Approval Notification";
				String message = "Your loan has been approved. Congratulations!";
				mailService.sendMail(customer.getCustomerEmail(), subject, message);
			} else {
				loan.setStatus("Rejected");
				String subject = "Loan Notification";
				String message = "We regret to inform you that your loan application has been rejected.";
				mailService.sendMail(customer.getCustomerEmail(), subject, message);
			}
		}

		return this.loansRepo.save(loan);
	}
	
	/******************************************************************************
	 * Method -isValidCollateral 
	 * Description -Checks whether the collateral is valid for particular loan.
	 * @param collateralValue, customerSalry, loanAmount -Based on these params, it will check whether collateral is valid or not.
	 * @return boolean -Boolean condition accordingly.
	 * @throws LoansException,  -Raised if there are any issues.
	 * Created by Shruti Betti
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	public boolean isValidCollateral(double collateralValue, double customerSalary, double loanAmount)
			throws LoansException {
		// Calculate the minimum required collateral value
		double minimumCollateralValue = ((2 * customerSalary) + (0.5 * loanAmount));

		// Check if the provided collateral value is greater than or equal to the
		// minimum required value
		if (collateralValue < minimumCollateralValue)
			return false;
		else
			return true;

	}
	
	/******************************************************************************
	 * Method -areValidDocuments
	 * Description -Checks whether submitted documents are fulfilled for particular loan.
	 * @param customer -For particular customer, it will check whether necessary documents are submitted or not.
	 * @return boolean -Boolean condition accordingly.
	 * Created by Shruti Betti
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	private boolean areValidDocuments(Customer customer) {
		// Assuming AppliedLoans has a List<Document> field representing the submitted
		// documents

		List<Document> submittedDocuments = customer.getDocuments();

		boolean hasAllRequiredDocuments = checkRequiredDocuments(submittedDocuments);

		return hasAllRequiredDocuments;
	}

	public boolean checkRequiredDocuments(List<Document> submittedDocuments) {
		// List of required document types
		List<DocumentType> requiredDocumentTypes = Arrays.asList(

				DocumentType.AADHAR, DocumentType.PAN, DocumentType.INCOME, DocumentType.DEGREE, DocumentType.ADDRESS

		);

		for (DocumentType requiredType : requiredDocumentTypes) {
			boolean hasRequiredType = false;
			for (Document submittedDoc : submittedDocuments) {
				if (submittedDoc.getDocumentType() == requiredType) {
					hasRequiredType = true;

				}
			}
			if (!hasRequiredType) {
				return false; // Required document type is missing
			}
		}

		return true; // All required documents are present
	}
	
	/******************************************************************************
	 * Method -isValidCreditScore
	 * Description -Checks whether the credit score is valid or not for particular customer.
	 * @param customer -For particular customer, it will check whether the credit score is valid or not.
	 * @return boolean -Boolean condition accordingly.
	 * Created by Shruti Betti
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	public boolean isValidCreditScore(Customer customer) {
		Integer requiredCreditScore = 670;
		Optional<Customer> customerOpt = this.customerRepo.findById(customer.getCustomerId());

		Integer customerCreditScore = customerOpt.get().getCustomerCreditScore();
		if (customerCreditScore >= requiredCreditScore) {
			return true;
		} else
			return false;
	}

	/******************************************************************************
	 * Method -getPendingLoans 
	 * Description -Gets all loans with status as pending with its details. This function is for the admin.
	 * @return List<Loan> -List of all pending loans 
	 * Created by Shruti Betti 
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@Override
	public Collection<Loan> getPendingLoans() throws LoansException {

		List<Loan> pendingLoans = new ArrayList<>();

		try {
			// Assuming you have a method to fetch loans from your data source
			List<Loan> allLoans = this.loansRepo.findAll();

			// Filter pending loans based on their status
			for (Loan loan : allLoans) {
				if ("pending".equalsIgnoreCase(loan.getStatus())) {
					pendingLoans.add(loan);
				}
			}
		} catch (Exception e) {
			throw new LoansException("Error fetching pending loans" + e);
		}

		return pendingLoans;
	}

	/******************************************************************************
	 * Method -getApprovedLoans 
	 * Description -Gets all loans with status as approve with its details. This function is for the admin.
	 * @return List<Loan> -List of all aproved loans.
	 *  Created by Shruti Betti 
	 *  Created Date 12-Sept-2023
	 ******************************************************************************/

	@Override
	public Collection<Loan> getApprovedLoans() throws LoansException {
		List<Loan> approvedLoans = new ArrayList<>();

		try {
			// Assuming you have a method to fetch loans from your data source
			List<Loan> allLoans = this.loansRepo.findAll();

			// Filter pending loans based on their status
			for (Loan loan : allLoans) {
				if ("approved".equalsIgnoreCase(loan.getStatus())) {
					approvedLoans.add(loan);
				}
			}
		} catch (Exception e) {
			throw new LoansException("Error fetching pending loans" + e);
		}

		return approvedLoans;
	}

	/******************************************************************************
	 * Method -getAllLoans 
	 * Description -Gets all loans with its details. This function is for the admin.
	 * @return List<Loan> -List of all loans
	 * Created by Shruti Betti 
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@Override
	public Collection<Loan> getAllLoans() throws LoansException {

		return this.loansRepo.findAll();
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

	@Override
	public Boolean validateAdmin(AdminLogin adminDetailDto) throws AdminException {

		String email = adminDetailDto.getAdminEmail();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Optional<Admin> optAdmin = adminRepo.findByAdminEmail(email);
		if (optAdmin.isPresent()) {
			Admin dbAdmin = optAdmin.get();
			// if(loginDetailsDto.getCustomerPassword().equals(dbCustomer.getCustomerPassword()))
			if (passwordEncoder.matches(adminDetailDto.getPassword(), dbAdmin.getPassword())) {
				return true;
			} else {
				return false;
			}
		}
		throw new AdminException("Admin not found with this email");

	}

}
