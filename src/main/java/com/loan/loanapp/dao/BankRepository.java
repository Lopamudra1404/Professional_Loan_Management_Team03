package com.loan.loanapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.loanapp.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer> {

	Optional<Bank> findByBankEmail(String email);

}
