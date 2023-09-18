package com.loan.loanapp.dto;
 
public class LoanCalculatorResultDto {
    private double monthlyPayment;
    private double totalAmountPaid;
    private double totalInterestPaid;
   
     public LoanCalculatorResultDto() {
          super();
     }
 
     public LoanCalculatorResultDto(double monthlyPayment, double totalAmountPaid, double totalInterestPaid) {
          super();
          this.monthlyPayment = monthlyPayment;
          this.totalAmountPaid = totalAmountPaid;
          this.totalInterestPaid = totalInterestPaid;
     }
 
     public double getMonthlyPayment() {
          return monthlyPayment;
     }
 
     public void setMonthlyPayment(double monthlyPayment) {
          this.monthlyPayment = monthlyPayment;
     }
 
     public double getTotalAmountPaid() {
          return totalAmountPaid;
     }
 
     public void setTotalAmountPaid(double totalAmountPaid) {
          this.totalAmountPaid = totalAmountPaid;
     }
 
     public double getTotalInterestPaid() {
          return totalInterestPaid;
     }
 
     public void setTotalInterestPaid(double totalInterestPaid) {
          this.totalInterestPaid = totalInterestPaid;
     }
    
   
 
}