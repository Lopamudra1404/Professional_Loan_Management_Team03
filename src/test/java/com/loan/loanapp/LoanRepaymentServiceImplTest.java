package com.loan.loanapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.loan.loanapp.dao.LoanAccountRepository;
import com.loan.loanapp.dao.LoanRepaymentRepository;
import com.loan.loanapp.dto.LoanRepaymentDto;
import com.loan.loanapp.entity.LoanAccount;
import com.loan.loanapp.exception.LoanRepaymentException;
import com.loan.loanapp.service.LoanRepaymentServiceImpl;

/******************************************************************************
 * Test -LoanRepaymentServiceImplTest
 * Description -JUNIT tests to enhance qualitycode of loanRepayment methods.
 * Created by Shruti Betti 
 * Created Date 12-Sept-2023
 ******************************************************************************/

class LoanRepaymentServiceImplTest {

	@Mock
	private LoanAccountRepository loanAccountRepository;

	@Mock
	private LoanRepaymentRepository loanRepaymentRepository;

	private LoanRepaymentServiceImpl loanRepaymentService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		loanRepaymentService = new LoanRepaymentServiceImpl();
	}

	// @Test
	void testCalculateAndStoreLoanRepayment_NoLateRepayment() throws LoanRepaymentException {

		LoanRepaymentDto repaymentDTO = new LoanRepaymentDto();
		repaymentDTO.setMonthlyAmountRepayed(500.0);
		repaymentDTO.setRepaymentDate(LocalDate.now());
		Integer loanAccountId = 1;

		loanRepaymentService.calculateAndStoreLoanRepayment(repaymentDTO, loanAccountId);

	}

	// @Test
	void testCalculateAndStoreLoanRepayment_LateRepayment() throws LoanRepaymentException {
		LoanAccount loanAccount = new LoanAccount(); // Create a LoanAccount object
		Integer loanAccountId = 1;

		when(loanAccountRepository.findById(any())).thenReturn(Optional.of(loanAccount));

		LocalDate repaymentDate = LocalDate.now().plusMonths(2);

		LoanRepaymentDto repaymentDTO = new LoanRepaymentDto();
		repaymentDTO.setMonthlyAmountRepayed(500.0);
		repaymentDTO.setRepaymentDate(repaymentDate);

		loanRepaymentService.calculateAndStoreLoanRepayment(repaymentDTO, loanAccountId);

		assertEquals(500.0, loanAccount.getBalance());
	}

}
