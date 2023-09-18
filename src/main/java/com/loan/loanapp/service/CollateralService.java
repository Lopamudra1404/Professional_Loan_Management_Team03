package com.loan.loanapp.service;

import com.loan.loanapp.entity.Collateral;
import com.loan.loanapp.exception.CollateralException;

public interface CollateralService {

	Collateral addCollateral(Collateral newCollateral, Integer loanId) throws CollateralException;

}