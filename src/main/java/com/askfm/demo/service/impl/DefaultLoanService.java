package com.askfm.demo.service.impl;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.dto.LoanDto;
import com.askfm.demo.entity.Loan;
import com.askfm.demo.exception.LoanNotFoundException;
import com.askfm.demo.mapper.LoanMapper;
import com.askfm.demo.repository.LoanRepository;
import com.askfm.demo.service.BlacklistService;
import com.askfm.demo.service.LoanService;
import com.askfm.demo.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultLoanService implements LoanService {

  private final LoanRepository loanRepository;
  private final LoanMapper loanMapper;
  private final UserService userService;
  private final BlacklistService blacklistService;

  @Override
  @Transactional
  public void applyLoan(ApplyLoanRequestDto applyLoanRequestDto) {
    blacklistService.checkIfNotBanned(applyLoanRequestDto.getPersonalId());
    var loan = loanMapper.toEntity(applyLoanRequestDto);
    var user = userService.getUserByPersonalId(applyLoanRequestDto.getPersonalId())
        .orElse(userService.registerUser(applyLoanRequestDto));
    loan.setUser(user);
    loanRepository.save(loan);
  }

  @Override
  @Transactional
  public void approveLoan(long loanId) {
    var loan = loanRepository.findById(loanId).orElseThrow(
        () -> new LoanNotFoundException("Loan with id = " + loanId + " does not exist")
    );

    blacklistService.checkIfNotBanned(loan.getUser().getPersonalId());

    var termEndDate = Instant.now().plus(loan.getTerm(), ChronoUnit.DAYS);
    loan.setTermEndDate(termEndDate);
    loan.setApproved(true);
    loanRepository.save(loan);
  }

  private List<LoanDto> convertToDtoList(List<Loan> loansList) {
    return loansList.stream()
        .map(loanMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<LoanDto> getAllApprovedLoans() {
    return convertToDtoList(loanRepository.findAllByApprovedIs(true));
  }

  @Override
  public List<LoanDto> getAllUserApprovedLoans(long userId) {
    return convertToDtoList(loanRepository.findAllByApprovedIsTrueAndUserIdIs(userId));
  }
}
