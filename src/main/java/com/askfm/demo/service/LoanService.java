package com.askfm.demo.service;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.dto.LoanDto;

import java.util.List;

public interface LoanService {
  void applyLoan(ApplyLoanRequestDto applyLoanRequestDto, String countryCode);

  void approveLoan(long loanId);

  List<LoanDto> getAllApprovedLoans();

  List<LoanDto> getAllUserApprovedLoans(long userId);
}
