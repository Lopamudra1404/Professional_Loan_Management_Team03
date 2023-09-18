package com.loan.loanapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.loan.loanapp.dao.AdminRepository;
import com.loan.loanapp.dao.CustomerRepository;
import com.loan.loanapp.dao.LoansRepository;
import com.loan.loanapp.dto.AdminLogin;
import com.loan.loanapp.entity.Admin;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Document;
import com.loan.loanapp.entity.DocumentType;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanType;
import com.loan.loanapp.exception.AdminException;
import com.loan.loanapp.exception.LoansException;
import com.loan.loanapp.service.AdminServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/******************************************************************************
 * Test -AdminServiceImplTest
 * Description -JUNIT tests to enhance qualitycode of Admin methods.
 * Created by Shruti Betti 
 * Created Date 12-Sept-2023
 ******************************************************************************/

class AdminServiceImplTest {

	@Mock
	private AdminRepository adminRepo;

	@Mock
	private CustomerRepository customerRepo;

	@Mock
	private LoansRepository loansRepo;

	@InjectMocks
	private AdminServiceImpl adminService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testIsValidCollateral_ValidCollateral() throws LoansException {
		double collateralValue = 2600000.0;
		double customerSalary = 800000.0;
		double loanAmount = 2000000.0;

		assertTrue(adminService.isValidCollateral(collateralValue, customerSalary, loanAmount));
	}

	@Test
	void testIsValidCollateral_InvalidCollateral() throws LoansException {
		double collateralValue = 1600000.0; // Less than the minimum required collateral
		double customerSalary = 800000.0;
		double loanAmount = 2000000.0;

		assertFalse(adminService.isValidCollateral(collateralValue, customerSalary, loanAmount));
	}

	@Test
	void testCheckRequiredDocuments_AllRequiredDocumentsPresent() {
		// Assuming you have a method to fetch the required document types
		List<DocumentType> requiredDocumentTypes = Arrays.asList(DocumentType.AADHAR, DocumentType.PAN,
				DocumentType.INCOME, DocumentType.DEGREE, DocumentType.ADDRESS);

		List<Document> submittedDocuments = new ArrayList<>();
		for (DocumentType type : requiredDocumentTypes) {
			submittedDocuments.add(new Document(0, "", type));
		}

		boolean result = adminService.checkRequiredDocuments(submittedDocuments);

		assertTrue(result, "All required documents are present");
	}

	@Test
	void testCheckRequiredDocuments_MissingRequiredDocuments() {
		List<DocumentType> requiredDocumentTypes = Arrays.asList(DocumentType.AADHAR, DocumentType.PAN,
				DocumentType.INCOME, DocumentType.DEGREE, DocumentType.ADDRESS);

		// Create submitted documents, missing one required type
		List<Document> submittedDocuments = new ArrayList<>();
		for (DocumentType type : requiredDocumentTypes) {
			if (type != DocumentType.PAN) {
				submittedDocuments.add(new Document(0, "", type));
			}
		}

		boolean result = adminService.checkRequiredDocuments(submittedDocuments);

		assertFalse(result, "Missing a required document");
	}

	@Test
	void testIsValidCreditScore_ValidCreditScore() {
		Customer customer = new Customer();
		customer.setCustomerCreditScore(700);

		when(customerRepo.findById(any())).thenReturn(Optional.of(customer));

		boolean result = adminService.isValidCreditScore(customer);

		assertTrue(result, "Valid credit score");
	}

	@Test
	void testIsValidCreditScore_InvalidCreditScore() {
		Customer customer = new Customer();
		customer.setCustomerCreditScore(500);

		when(customerRepo.findById(any())).thenReturn(Optional.of(customer));

		boolean result = adminService.isValidCreditScore(customer);

		// Assert the result
		assertFalse(result, "Invalid credit score");
	}

	@Test
	void testGetApprovedLoans() throws LoansException {

		Loan loan1 = new Loan();
		loan1.setLoanId(1);
		loan1.setLoanType(LoanType.CA_Loan);
		loan1.setLoanName("");
		loan1.setLoanAmount(200000.0);
		loan1.setLoanIntrest(8.5);
		loan1.setLoanTenture(24);
		loan1.setLoanEMI(10000.0);
		loan1.setLoanDescription("");
		loan1.setStatus("Approved");

		Loan loan2 = new Loan();
		loan2.setLoanId(2);
		loan2.setLoanType(LoanType.CS_Loan);
		loan2.setLoanName("");
		loan2.setLoanAmount(50000.0);
		loan2.setLoanIntrest(12.0);
		loan2.setLoanTenture(12);
		loan2.setLoanEMI(4500.0);
		loan2.setLoanDescription("");
		loan2.setStatus("Approved");

		List<Loan> approvedLoans = new ArrayList<>();
		approvedLoans.add(loan1);
		approvedLoans.add(loan2);

		when(loansRepo.findAll()).thenReturn(approvedLoans);

		Collection<Loan> result = adminService.getApprovedLoans();

		assertEquals(approvedLoans, result, "Returned list matches approved loans");
	}

	@Test
	void testGetAllLoans() throws LoansException {
		// Create a list of loans
		Loan loan1 = new Loan();
		loan1.setLoanId(1);
		loan1.setLoanType(LoanType.CA_Loan);
		loan1.setLoanName("");
		loan1.setLoanAmount(200000.0);
		loan1.setLoanIntrest(8.5);
		loan1.setLoanTenture(24);
		loan1.setLoanEMI(10000.0);
		loan1.setLoanDescription("");
		loan1.setStatus("Pending");

		Loan loan2 = new Loan();
		loan2.setLoanId(2);
		loan2.setLoanType(LoanType.CS_Loan);
		loan2.setLoanName("");
		loan2.setLoanAmount(50000.0);
		loan2.setLoanIntrest(12.0);
		loan2.setLoanTenture(12);
		loan2.setLoanEMI(4500.0);
		loan2.setLoanDescription("");
		loan2.setStatus("Approved");

		Loan loan3 = new Loan();
		loan2.setLoanId(2);
		loan2.setLoanType(LoanType.CS_Loan);
		loan2.setLoanName("");
		loan2.setLoanAmount(50000.0);
		loan2.setLoanIntrest(12.0);
		loan2.setLoanTenture(12);
		loan2.setLoanEMI(4500.0);
		loan2.setLoanDescription("");
		loan2.setStatus("Rejected");

		List<Loan> allLoans = new ArrayList<>();
		allLoans.add(loan1);
		allLoans.add(loan2);
		allLoans.add(loan3);

		when(loansRepo.findAll()).thenReturn(allLoans);

		Collection<Loan> result = adminService.getAllLoans();

		assertEquals(allLoans, result, "Returned list matches all loans");
	}

	// @Test
	void testValidateAdmin_ValidAdmin() throws AdminException {
		String validEmail = "admin@gmail.com";
		String validPassword = "Rewq@123";

		AdminLogin adminLogin = new AdminLogin(validEmail, validPassword);
		when(adminRepo.findByAdminEmail(validEmail))
				.thenReturn(Optional.of(new Admin(111, "Administrator", validPassword, validEmail, 34531, null, null)));

		// Call the method under test
		Boolean result = adminService.validateAdmin(adminLogin);

		assertTrue(result, "Valid admin");
	}

}
