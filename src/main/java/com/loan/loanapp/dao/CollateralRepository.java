package com.loan.loanapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.loanapp.entity.Collateral;

public interface CollateralRepository extends JpaRepository<Collateral, Integer> {

	Optional<Collateral> findByLoan_loanId(Integer loanId);

}
