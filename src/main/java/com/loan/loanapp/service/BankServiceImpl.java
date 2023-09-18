package com.loan.loanapp.service;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
import com.loan.loanapp.exception.CustomerException;
import com.loan.loanapp.exception.LoanDisbursementException;
import com.loan.loanapp.exception.LoansException;
/******************************************************************************
 
* @author Rana Shaikh and Shruti Betti
 
* Description BankService is responsible for managing bank-related operations via RESTful endpoints. It handles customer's
 
  account generation,loan disbursement,view the disbursed loans,view the loan accounts. It provides endpoints to interact with user data in the system.
 
* Version 1.0 Created Date 12-Sept-2023
 
******************************************************************************/
 
@Service
public class BankServiceImpl implements BankService {
 
     @Autowired
     BankRepository bankRepo;
 
     @Autowired
     LoansRepository loansRepo;
 
     @Autowired
     LoanDisbursementRepository loanDisbursementRepo;
 
     @Autowired
     LoanAccountRepository loanAccountRepo;
 
     @Autowired
     MailService mailService;
    
    
     /******************************************************************************
 
     * Method -generateLoanAccount.
 
     * Description -Create Customer's loan account for the loanId.
    
      * @param LoanId -loan account is created for the loan Id.
 
     * @return LoanAccount -Returns LoanAccount entity with updated status(account generated).
 
     * @throws LoansException-Raised if the loan account already exist.
 
     * Created by Shruti Betti
 
     * Created Date 12-Sept-2023
 
     ******************************************************************************/
 
     @Override
     public LoanAccount generateLoanAccount(Integer loanId) throws LoansException {
 
          Optional<Loan> loan = loansRepo.findById(loanId);
          List<Loan> approvedLoans = loansRepo.findByStatusIgnoreCase("Approved");
          if (!approvedLoans.isEmpty()) {
              LoanAccount existingLoanAccountOpt = loanAccountRepo.findByLoan_loanId(loanId);
 
              if (existingLoanAccountOpt != null) {
                   // Handle the case where a loan account already exists
                   throw new LoansException("A loan account already exists for loan ID: " + loanId);
              }
              // Create a new loan account
              LoanAccount loanAccount = new LoanAccount();
 
              loanAccount.setAccountNo(generateAccountNumber()); // Implement account number generation
              Customer customer = loan.get().getCustomer();
              loanAccount.setCustomer(customer);
              loanAccount.setLoan(loan.get());
              LoanAccount savedLoanAccount = loanAccountRepo.save(loanAccount);
              // Set the loan account reference in the Loan entity
              loan.get().setLoanAccount(savedLoanAccount);
 
              // Update the Loan entity to save the loan account reference
              // loansRepo.save(loan.get());
 
              loan.get().setStatus("Account Generated");
              loansRepo.save(loan.get());
 
              String subject = "Loan Account Created";
 
              String message = "Your loan account has been generated. Your Loan AccountNo is: "
                        + loanAccount.getAccountNo() +
 
                        ". Your loan will disburse soon. Thank you!";
 
              mailService.sendMail(customer.getCustomerEmail(), subject, message);
 
              return savedLoanAccount;
          }
 
          return null; // Return null if the loan is not approved or not found
     }
 
     public String generateAccountNumber() {
          Random random = new Random();
          int accountNumber = 1000000000 + random.nextInt(900000000); // Generates a 10-digit number
          return Integer.toString(accountNumber);
     }
     /******************************************************************************
 
     * Method -disburseLoanByLoanId.
 
     * Description -disburse the loan amount to customer's loan account for the loanId.
    
      * @param LoanId -loan amount is disbursed for the loan Id.
 
     * @return LoanDisbursement -Returns LoanDisbursement entity with updated status(disbursed).
 
     * @throws LoansException,LoanDisbursementException-Raised if the loan is not found,loan account is not created for the loan,loan is already disbursed
 
     * Created by Rana Shaikh
 
     * Created Date 12-Sept-2023
 
     ******************************************************************************/
 
     @Override
     public LoanDisbursement disburseLoanByLoanId(Integer loanId) throws LoanDisbursementException, LoansException {
          // Find the loan by loanId
          Optional<Loan> loanOpt = loansRepo.findById(loanId);
 
          if (!loanOpt.isPresent()) {
              throw new LoansException("Loan not found with ID: " + loanId);
          }
 
          Loan loan = loanOpt.get();
          // Check if a loan account exists
          LoanAccount loanAccount = loanAccountRepo.findByLoan_loanId(loan.getLoanId());
 
          if (loanAccount == null) {
              throw new LoanDisbursementException("Loan account is not created for this loanId.");
          }
 
          if (!loan.getStatus().equalsIgnoreCase("account generated")) {
              throw new LoanDisbursementException("Loan cannot be disbursed.");
          }
 
          if (loan.getStatus().equalsIgnoreCase("disbursed")) {
              throw new LoanDisbursementException("Loan is already disbursed.");
          }
 
          loan.setStatus("disbursed");
          loansRepo.save(loan);
 
          LoanDisbursement loanDisbursement = new LoanDisbursement();
          loanDisbursement.setLoan(loan);
          loanDisbursement.setLoanDisbursementAmount(loan.getLoanAmount());
 
          // Move the loanDisbursement status setting inside the if block
          loanDisbursement.setLoanDisbursementStatus("disbursed");
 
          loan.setLoanDisbursement(loanDisbursement);
          loanDisbursement = loanDisbursementRepo.save(loanDisbursement);
 
          // Update the loan account balance
          loanAccount.setBalance(loan.getLoanAmount());
          loanAccount.setTotalAmount(loan.getLoanAmount());
          loanAccountRepo.save(loanAccount);
 
          String subject = "Loan Disbursed";
 
          String message = "Your loan with loan ID: " + loanId + " has been disbursed. Thank you.";
 
          mailService.sendMail(loan.getCustomer().getCustomerEmail(), subject, message);
 
          return loanDisbursement;
     }
/******************************************************************************
 
          * Method -getAllDisbursedLoans
 
          * Description -Gets all loans with status as disbursed with its details. This function is for the bank.
 
          * @return List<LoanDisbursement> -List of all disbursed loans
 
          * Created by Rana Shaikh
 
          * Created Date 12-Sept-2023
 
          ******************************************************************************/
 
     @Override
     public Collection<LoanDisbursement> getAllDisbursedLoans() {
          return this.loanDisbursementRepo.findAll();
     }
 
     @Override
     public LoanDisbursement getLoanDisbursementById(Integer id) throws LoanDisbursementException {
          Optional<LoanDisbursement> loanDisbursementOpt = this.loanDisbursementRepo.findById(id);
          if (!loanDisbursementOpt.isPresent())
              throw new LoanDisbursementException("LoanDisbursement doesn't exist for id: " + id);
          return loanDisbursementOpt.get();
     }
     /******************************************************************************
 
     * Method -BankLogin
 
     * Description -Logs in the bank based on the provided BankLoginDetailsDto.
 
     * @param BankLoginDetailsDto -The bank data to be validated.
 
     * @return Boolean -Returns true or false accordingly.
 
     * @throws BankException -Raised if there are issues with login.
 
     * Created by Rana Shaikh
 
     * Created Date 12-Sept-2023
 
     ******************************************************************************/
 
     @Override
     public Boolean validateBank(BankLogin bankDetailDto) throws BankException {
 
          String email = bankDetailDto.getBankEmail();
          BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
          Optional<Bank> optBank = bankRepo.findByBankEmail(email);
          if (optBank.isPresent()) {
              Bank dbBank = optBank.get();
              // if(loginDetailsDto.getCustomerPassword().equals(dbCustomer.getCustomerPassword()))
              if (passwordEncoder.matches(bankDetailDto.getBankPassword(), dbBank.getBankPassword())) {
                   return true;
              } else {
                   return false;
              }
          }
          throw new BankException("Bank not found with this email");
 
     }
     /******************************************************************************
 
     * Method -getApprovedLoans
 
     * Description -Gets all loans with status as approve with its details. This function is for the bank.
 
     * @return List<Loan> -List of all approved loans for disbursing the loan.
 
     *  Created by Rana Shaikh
 
     *  Created Date 12-Sept-2023
 
     ******************************************************************************/
 
     @Override
     public Collection<Loan> getApprovedLoans() throws LoansException {
          // TODO Auto-generated method stub
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
 
     * Method - getApprovedAndAccountGeneratedLoans
 
     * Description -Gets all loans with status as approve and account generated with its details. This function is for the bank.
 
     * @return List<Loan> -List of all aproved and account generated loans for disbursing the loan.
 
     *  Created by Rana Shaikh
 
     *  Created Date 12-Sept-2023
 
     ******************************************************************************/
 
     @Override
     public Collection<Loan> getApprovedAndAccountGeneratedLoans() throws LoansException {
          // TODO Auto-generated method stub
//        List<String> statuses = Arrays.asList("Approved", "Account Generated");
          List<Loan> approvedAndGeneratedLoans = new ArrayList<>();
          try {
              List<Loan> allLoans = this.loansRepo.findAll();
 
              for (Loan loan : allLoans) {
                   if ("approved".equalsIgnoreCase(loan.getStatus())
                             || "Account Generated".equalsIgnoreCase(loan.getStatus())) {
                        approvedAndGeneratedLoans.add(loan);
                   }
              }
          } catch (Exception e) {
              throw new LoansException("Error fetching pending loans" + e);
 
              // Call the custom repository method to fetch loans with the specified statuses
 
          }
          return approvedAndGeneratedLoans;
     }
    
     /******************************************************************************
 
     * Method -  getAllAccounts
 
     * Description -Gets all loans accounts.
 
     * @return List<LoanAccount> -List of all loan accounts.
 
     *  Created by Rana Shaikh
 
     *  Created Date 12-Sept-2023
 
     ******************************************************************************/
     @Override
     public Collection<LoanAccount> getAllAccounts() {
          // TODO Auto-generated method stub
          return this.loanAccountRepo.findAll();
     }
}
 