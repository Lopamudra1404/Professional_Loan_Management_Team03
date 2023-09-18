package com.loan.loanapp.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.loan.loanapp.entity.Loan;

public interface LoansRepository extends JpaRepository<Loan, Integer> {

	List<Loan> findByStatusIgnoreCase(String status);

}
