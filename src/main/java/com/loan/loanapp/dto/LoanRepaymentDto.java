package com.loan.loanapp.dto;
 
import java.time.LocalDate;
 
public class LoanRepaymentDto {
     private Double monthlyAmountRepayed;
     private LocalDate repaymentDate;
 
     public LoanRepaymentDto() {
          super();
          this.repaymentDate = LocalDate.now();
     }
 
     public LoanRepaymentDto(Double monthlyAmountRepayed, LocalDate repaymentDate) {
          super();
          this.monthlyAmountRepayed = monthlyAmountRepayed;
          this.repaymentDate = LocalDate.now();
     }
 
     public Double getMonthlyAmountRepayed() {
          return monthlyAmountRepayed;
     }
 
     public void setMonthlyAmountRepayed(Double monthlyAmountRepayed) {
          this.monthlyAmountRepayed = monthlyAmountRepayed;
     }
 
     public LocalDate getRepaymentDate() {
          return repaymentDate;
     }
 
     public void setRepaymentDate(LocalDate repaymentDate) {
          this.repaymentDate = repaymentDate;
     }
 
}