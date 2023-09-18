package com.loan.loanapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.loanapp.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Optional<Customer> findByCustomerEmail(String email);
	Optional<Customer> findByCustomerId(Integer id);

}
