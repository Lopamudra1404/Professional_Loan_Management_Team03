package com.loan.loanapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.loan.loanapp.entity.Collateral;
import com.loan.loanapp.exception.CollateralException;
import com.loan.loanapp.service.CollateralService;
import com.loan.loanapp.service.LoansService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CollateralController {

	@Autowired
	CollateralService collateralService;
	@Autowired
	LoansService loansService;

	@PostMapping("/customer/collateral/{loanId}")

	public Collateral addCollateral(@RequestBody Collateral newCollateral, @PathVariable Integer loanId)
			throws CollateralException {
		return this.collateralService.addCollateral(newCollateral, loanId);
	}
}
