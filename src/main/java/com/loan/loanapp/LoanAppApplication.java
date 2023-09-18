package com.loan.loanapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.loan.loanapp.dao.AdminRepository;
import com.loan.loanapp.dao.BankRepository;
import com.loan.loanapp.dao.CustomerRepository;
import com.loan.loanapp.entity.Admin;
import com.loan.loanapp.entity.Bank;

@SpringBootApplication
public class LoanAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LoanAppApplication.class, args);
	}

	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	AdminRepository adminRepo;
	@Autowired
	BankRepository bankRepo;

	// LocalDate today = LocalDate.parse("2019-03-29");

	@Override
	public void run(String... args) throws Exception {

		Admin admin = new Admin(111, "Administrator", "Rewq@123", "admin@gmail.com", 34531, null, null);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(admin.getPassword());
		admin.setPassword(encodedPassword);
		// System.out.println(this.adminRepo.save(admin));
		Bank bank = new Bank(11011, "Bajaj", "bajaj@gmail.com", "Fdsa@123", 411042, "Kalyani nagar, Pune", 98907);

		BCryptPasswordEncoder passwordEncoderBank = new BCryptPasswordEncoder();
		String encodedPasswordBank = passwordEncoderBank.encode(bank.getBankPassword());
		bank.setBankPassword(encodedPasswordBank);
		 //System.out.println(this.bankRepo.save(bank));

	}

}
