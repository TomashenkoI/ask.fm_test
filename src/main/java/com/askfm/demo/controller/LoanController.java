package com.askfm.demo.controller;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.dto.LoanDto;
import com.askfm.demo.service.CountryDefinitionService;
import com.askfm.demo.service.LoanService;
import com.askfm.demo.service.RequestFrequencyLimiter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

  private final LoanService loanService;
  private final CountryDefinitionService countryDefinitionService;
  private final RequestFrequencyLimiter requestFrequencyLimiter;

  @PostMapping("/apply")
  @ResponseStatus(HttpStatus.CREATED)
  public void applyLoan(@RequestBody ApplyLoanRequestDto applyLoanRequestDto, HttpServletRequest request) {
    var countryCode = countryDefinitionService.getCountryCode(request.getRemoteAddr());
    requestFrequencyLimiter.checkRequestsFrequency(countryCode);
    loanService.applyLoan(applyLoanRequestDto, countryCode);
  }

  @PutMapping("/approve/{loanId}")
  public void approveLoan(@PathVariable(name = "loanId") int loanId) {
    loanService.approveLoan(loanId);
  }

  @GetMapping("/user/{userId}/approved")
  public List<LoanDto> getAllApprovedUserLoans(@PathVariable("userId") long userId) {
    return loanService.getAllUserApprovedLoans(userId);
  }

  @GetMapping("/approved")
  public List<LoanDto> getAllApprovedLoans() {
    return loanService.getAllApprovedLoans();
  }

}
