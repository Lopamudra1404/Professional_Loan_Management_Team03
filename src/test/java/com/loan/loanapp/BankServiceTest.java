package com.loan.loanapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import com.loan.loanapp.dao.BankRepository;
import com.loan.loanapp.dao.LoanAccountRepository;
import com.loan.loanapp.dao.LoanDisbursementRepository;
import com.loan.loanapp.dao.LoansRepository;
import com.loan.loanapp.dto.BankLogin;
import com.loan.loanapp.entity.Bank;
import com.loan.loanapp.entity.Customer;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanAccount;
import com.loan.loanapp.entity.LoanDisbursement;
import com.loan.loanapp.exception.BankException;
import com.loan.loanapp.exception.LoanDisbursementException;
import com.loan.loanapp.exception.LoansException;
import com.loan.loanapp.service.BankServiceImpl;
import com.loan.loanapp.service.MailService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BankServiceTest {

	@InjectMocks
	private BankServiceImpl bankService;

	@Mock
	private BankRepository bankRepo;

	@Mock
	private LoansRepository loansRepo;

	@Mock
	private LoanDisbursementRepository loanDisbursementRepo;

	@Mock
	private LoanAccountRepository loanAccountRepo;

	@Mock
	private MailService mailService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}


	// @Test
	void testGenerateLoanAccount_Success() throws LoansException {
		// Arrange
		Integer loanId = 123;
		Loan loan = new Loan();
		loan.setStatus("Approved");
		loan.setLoanId(loanId);
		Customer customer = new Customer();
		loan.setCustomer(customer);

		when(loansRepo.findById(loanId)).thenReturn(Optional.of(loan));
		when(loansRepo.findByStatusIgnoreCase("Approved")).thenReturn(Arrays.asList(new Loan()));
		when(loanAccountRepo.findByLoan_loanId(loanId)).thenReturn(null);
		when(loanAccountRepo.save(any(LoanAccount.class))).thenReturn(new LoanAccount());

		// Act
		LoanAccount result = bankService.generateLoanAccount(loanId);

		// Assert
		assertNotNull(result);
		assertEquals(customer, result.getCustomer());
		assertEquals(loan, result.getLoan());
		verify(loanAccountRepo, times(1)).save(any(LoanAccount.class));
		verify(loansRepo, times(1)).save(loan);
	}

	@Test
	void testGenerateLoanAccount_ThrowsExceptionWhenAccountExists() {
		// Arrange
		Integer loanId = 123;
		LoanAccount existingLoanAccount = new LoanAccount();

		when(loansRepo.findById(loanId)).thenReturn(Optional.of(new Loan()));
		// when(loansRepo.findByStatusIgnoreCase("Approved")).thenReturn(List.of(new
		// Loan()));
		when(loansRepo.findByStatusIgnoreCase("Approved")).thenReturn(Arrays.asList(new Loan()));

		when(loanAccountRepo.findByLoan_loanId(loanId)).thenReturn(existingLoanAccount);

		// Act & Assert
		assertThrows(LoansException.class, () -> {
			bankService.generateLoanAccount(loanId);
		}, "A loan account already exists for loan ID: " + loanId);

		verify(loanAccountRepo, never()).save(any(LoanAccount.class));
		verify(loansRepo, never()).save(any(Loan.class));
	}

	@Test
	void testGenerateLoanAccount_ReturnsNullWhenLoanNotApproved() throws LoansException {
		// Arrange
		Integer loanId = 123;
		Loan loan = new Loan();
		loan.setStatus("Pending");

		when(loansRepo.findById(loanId)).thenReturn(Optional.of(loan));
		when(loansRepo.findByStatusIgnoreCase("Approved")).thenReturn(new ArrayList<>());

		// Act
		LoanAccount result = bankService.generateLoanAccount(loanId);

		// Assert
		assertNull(result);
		verify(loanAccountRepo, never()).save(any(LoanAccount.class));
		verify(loansRepo, never()).save(any(Loan.class));
	}

	// @Test
	void testDisburseLoanByLoanId_Success() throws LoanDisbursementException, LoansException {
		// Arrange
		Integer loanId = 123;
		Loan loan = new Loan();
		loan.setStatus("account generated");
		loan.setLoanId(loanId);
		LoanAccount loanAccount = new LoanAccount();

		when(loansRepo.findById(loanId)).thenReturn(Optional.of(loan));
		when(loanAccountRepo.findByLoan_loanId(loanId)).thenReturn(loanAccount);
		when(loanDisbursementRepo.save(any(LoanDisbursement.class))).thenReturn(new LoanDisbursement());

		// Act
		LoanDisbursement result = bankService.disburseLoanByLoanId(loanId);

		// Assert
		assertNotNull(result, "Result is null. Loan ID: " + loanId);
		assertEquals(loan, result.getLoan());
		assertEquals("disbursed", loan.getStatus());
		assertEquals("disbursed", result.getLoanDisbursementStatus());
		verify(loansRepo, times(1)).save(loan);
		verify(loanAccountRepo, times(1)).save(loanAccount);
	}

	@Test
	void testDisburseLoanByLoanId_ThrowsExceptionWhenLoanNotFound() {
		// Arrange
		Integer loanId = 123;
		when(loansRepo.findById(loanId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(LoansException.class, () -> {
			bankService.disburseLoanByLoanId(loanId);
		}, "Loan not found with ID: " + loanId);
	}

	@Test
	void testDisburseLoanByLoanId_ThrowsExceptionWhenLoanAccountNotCreated() {
		// Arrange
		Integer loanId = 123;
		Loan loan = new Loan();
		when(loansRepo.findById(loanId)).thenReturn(Optional.of(loan));
		when(loanAccountRepo.findByLoan_loanId(loanId)).thenReturn(null);

		// Act & Assert
		assertThrows(LoanDisbursementException.class, () -> {
			bankService.disburseLoanByLoanId(loanId);
		}, "Loan account is not created for this loanId.");
	}

	@Test
	void testDisburseLoanByLoanId_ThrowsExceptionWhenLoanAlreadyDisbursed() {
		// Arrange
		Integer loanId = 123;
		Loan loan = new Loan();
		loan.setStatus("disbursed");
		when(loansRepo.findById(loanId)).thenReturn(Optional.of(loan));

		// Act & Assert
		assertThrows(LoanDisbursementException.class, () -> {
			bankService.disburseLoanByLoanId(loanId);
		}, "Loan is already disbursed.");
	}

	@Test
	void testGetAllDisbursedLoans() {
		// Arrange
		List<LoanDisbursement> expectedDisbursements = new ArrayList<>();
		expectedDisbursements.add(new LoanDisbursement());
		expectedDisbursements.add(new LoanDisbursement());

		when(loanDisbursementRepo.findAll()).thenReturn(expectedDisbursements);

		// Act
		Collection<LoanDisbursement> result = bankService.getAllDisbursedLoans();

		// Assert
		assertNotNull(result);
		assertEquals(expectedDisbursements.size(), result.size());
	}

	@Test
	void testGetLoanDisbursementById_Success() throws LoanDisbursementException {
		// Arrange
		Integer id = 123;
		LoanDisbursement expectedDisbursement = new LoanDisbursement();
		expectedDisbursement.setLoanDisbursementId(id);

		when(loanDisbursementRepo.findById(id)).thenReturn(Optional.of(expectedDisbursement));

		// Act
		LoanDisbursement result = bankService.getLoanDisbursementById(id);

		// Assert
		assertNotNull(result);
		assertEquals(id, result.getLoanDisbursementId());
	}

	@Test
	void testGetLoanDisbursementById_ThrowsExceptionWhenNotFound() {
		// Arrange
		Integer id = 123;
		when(loanDisbursementRepo.findById(id)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(LoanDisbursementException.class, () -> {
			bankService.getLoanDisbursementById(id);
		}, "LoanDisbursement doesn't exist for id: " + id);
	}

	@Test
	void testValidateBank_Success() throws BankException {
		// Arrange
		String email = "bajaj@gmail.com";
		String password = "Fdsa@123";

		BankLogin bankLogin = new BankLogin(email, password);
		bankLogin.setBankEmail(email);
		bankLogin.setBankPassword(password);

		String hashedPassword = "$2a$10$8ZBeOWZPdB1vmPMPZ/1nuOee13N5HsItuaR7d5GNE9PhGt1UrmJku"; // BCrypt-hashed
																								// password

		Bank dbBank = new Bank();
		dbBank.setBankEmail(email);
		dbBank.setBankPassword(hashedPassword);

		when(bankRepo.findByBankEmail(email)).thenReturn(Optional.of(dbBank));

		// Act
		Boolean result = bankService.validateBank(bankLogin);

		// Assert
		assertTrue(result);
	}

	@Test
	void testValidateBank_ThrowsExceptionWhenBankNotFound() {
		// Arrange
		String email = "Baja@gmail.com";
		String password = "password";

		BankLogin bankLogin = new BankLogin(email, password);
		bankLogin.setBankEmail(email);
		bankLogin.setBankPassword(password);

		when(bankRepo.findByBankEmail(email)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(BankException.class, () -> {
			bankService.validateBank(bankLogin);
		}, "Bank not found with this email");
	}

	@Test
	void testGetApprovedLoans() throws LoansException {
		// Arrange
		List<Loan> allLoans = new ArrayList<>();
		Loan approvedLoan1 = new Loan();
		approvedLoan1.setStatus("Approved");
		Loan approvedLoan2 = new Loan();
		approvedLoan2.setStatus("Approved");
		Loan pendingLoan = new Loan();
		pendingLoan.setStatus("Pending");

		allLoans.add(approvedLoan1);
		allLoans.add(approvedLoan2);
		allLoans.add(pendingLoan);

		when(loansRepo.findAll()).thenReturn(allLoans);

		// Act
		Collection<Loan> result = bankService.getApprovedLoans();

		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	void testGetApprovedAndAccountGeneratedLoans() throws LoansException {
		// Arrange
		List<Loan> allLoans = new ArrayList<>();
		Loan approvedLoan = new Loan();
		approvedLoan.setStatus("Approved");
		Loan accountGeneratedLoan = new Loan();
		accountGeneratedLoan.setStatus("Account Generated");
		Loan pendingLoan = new Loan();
		pendingLoan.setStatus("Pending");

		allLoans.add(approvedLoan);
		allLoans.add(accountGeneratedLoan);
		allLoans.add(pendingLoan);

		when(loansRepo.findAll()).thenReturn(allLoans);

		// Act
		Collection<Loan> result = bankService.getApprovedAndAccountGeneratedLoans();

		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	void testGetAllAccounts() {
		// Arrange
		List<LoanAccount> expectedAccounts = new ArrayList<>();
		expectedAccounts.add(new LoanAccount());
		expectedAccounts.add(new LoanAccount());

		when(loanAccountRepo.findAll()).thenReturn(expectedAccounts);

		// Act
		Collection<LoanAccount> result = bankService.getAllAccounts();

		// Assert
		assertNotNull(result);
		assertEquals(expectedAccounts.size(), result.size());
	}
}
