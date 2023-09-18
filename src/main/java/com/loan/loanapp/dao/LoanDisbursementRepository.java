package com.loan.loanapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.loanapp.entity.LoanDisbursement;

public interface LoanDisbursementRepository extends JpaRepository<LoanDisbursement, Integer> {

}
