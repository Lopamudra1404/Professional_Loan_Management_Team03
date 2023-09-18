package com.loan.loanapp.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loan.loanapp.dao.LoanAccountRepository;
import com.loan.loanapp.dao.LoanRepaymentRepository;
import com.loan.loanapp.dto.LoanRepaymentDto;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanAccount;
import com.loan.loanapp.entity.LoanRepayment;
import com.loan.loanapp.exception.LoanRepaymentException;
import com.loan.loanapp.exception.LoansException;

/******************************************************************************
 * @author Rana Shaikh
 * Description LoanRepaymentService is responsible for managing repayments-related operations. It handles customer's
 *             repayments, due date management,as well as generated unique receipt numbers for each repayment on the monthly basis. 
 * Version 1.0 Created Date 12-Sept-2023
 ******************************************************************************/

@Service
public class LoanRepaymentServiceImpl implements LoanRepaymentService {

	@Autowired
	private LoanRepaymentRepository loanRepaymentRepository;

	@Autowired
	private LoanAccountRepository loanAccountRepository;
	
	/******************************************************************************
	 * Method -calculateAndStoreLoanRepayment 
	 * Description -When customer repay the loan EMI it will be calculated and stored and balance from loan account will be deducted.
	 * @param LoanRepaymentDto, LoanAccountId 
	 * @return void -LoanREpayment and loanAccount is been saved.
	 * @throws LoanRepaymentException -Raised if there are issues with repayments.
	 * Created by Rana Shaikh
	 * Created Date 12-Sept-2023
	 ******************************************************************************/

	@Override
	public void calculateAndStoreLoanRepayment(LoanRepaymentDto repaymentDTO, Integer loanAccountId)
			throws LoanRepaymentException {
		if (loanAccountId == null) {
			throw new IllegalArgumentException("Loan Account ID is null");
		}
		Double monthlyAmountRepayed = repaymentDTO.getMonthlyAmountRepayed();
		LocalDate repaymentDate = repaymentDTO.getRepaymentDate();
		// Retrieve the LoanDisbursement based on the ID
		Optional<LoanAccount> loanAccountOpt = loanAccountRepository.findById(loanAccountId);
		if (!loanAccountOpt.isPresent()) {
			throw new IllegalArgumentException("LoanAccount not found with ID: " + loanAccountId);
		}
		LoanAccount loanAccount1 = loanAccountOpt.get();
		Loan loan = loanAccount1.getLoan();

		LocalDate dueDate = calculateDueDate(repaymentDate);
		Double lateRepaymentPenalty = 0.0;
		if (repaymentDate.isAfter(dueDate.plusMonths(1))) {

			lateRepaymentPenalty = loan.getLoanEMI(); // Change this to your specific penalty calculation logic
		}

		// Calculate the total repayment amount, including the late repayment penalty
		Double totalRepaymentAmount = monthlyAmountRepayed + lateRepaymentPenalty;

		// Create a LoanRepayment entity to store the repayment details
		LoanRepayment loanRepayment = new LoanRepayment();
		loanRepayment.setMonthlyAmountRepayed(monthlyAmountRepayed.intValue()); // Assuming Integer type for
																				// monthlyAmountRepayed
		loanRepayment.setRepaymentDate(repaymentDate);
		loanRepayment.setLateRepaymentPenalty(lateRepaymentPenalty);
		loanRepayment.setLoanRepaymentDueDate(dueDate);

		LoanAccount loanAccount = loanAccountRepository.findByLoan_loanId(loan.getLoanId());// 13 loan id
		// Loan loan1 = loanAccount.getLoan();

		// Set the loan_id in the LoanRepayment entity
		loanRepayment.setLoan(loan);

		Double remainingBalance;
		LoanRepayment lastRepayment = loanRepaymentRepository
				.findTopByLoanLoanIdOrderByRepaymentDateDesc(loan.getLoanId());
		if (totalRepaymentAmount > loanAccount.getLoan().getLoanEMI()) {
			if (lastRepayment != null) {
				remainingBalance = loanAccount.getBalance() - totalRepaymentAmount;
			} else {
				remainingBalance = loanAccount.getTotalAmount() - totalRepaymentAmount;
			}
			loanAccount.setBalance(remainingBalance);
			loanAccountRepository.save(loanAccount);
			Boolean loanRepaymentStatus = (remainingBalance <= 0);
			loanRepayment.setLoanRepaymentStatus(loanRepaymentStatus);
			String repaymentReceipt1 = generateUniqueReceiptNumber();
			loanRepayment.setRepaymentRecipt(repaymentReceipt1);
			loanRepaymentRepository.save(loanRepayment);
		} else {
			throw new LoanRepaymentException(
					"Loan intallment you are paying is less than the required fixed EMI for a month");

		}

	}

	// Generate a unique receipt number (you can use UUID or any other method)
	private String generateUniqueReceiptNumber() {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
	}

	// Calculate due date based on repayment date (add one month)
	private LocalDate calculateDueDate(LocalDate repaymentDate) {
		int year = repaymentDate.getYear();
		int month = repaymentDate.getMonthValue();
		int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();
		return LocalDate.of(year, month, lastDayOfMonth);
	}

}