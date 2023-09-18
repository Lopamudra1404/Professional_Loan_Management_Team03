package com.loan.loanapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loan.loanapp.entity.Admin;
import com.loan.loanapp.entity.Customer;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	Optional<Admin> findByAdminEmail(String email);

}
