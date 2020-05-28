package com.askfm.demo.service.impl;


import com.askfm.demo.PostgresqlDbBaseTest;
import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.exception.LoanNotFoundException;
import com.askfm.demo.service.LoanService;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.spring.api.DBRider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.askfm.demo.TestConstantHolder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DBRider
@SpringBootTest
class DefaultLoanServiceIntegrationTest extends PostgresqlDbBaseTest {

  @Autowired
  private LoanService loanService;

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.INSERT,
      value = "datasets/loan/should-approve-loan.yml"
  )
  @ExpectedDataSet(value = "datasets/loan/should-approve-loan-expected.yml")
  @DisplayName("should approve loan")
  void shouldApproveLoan() {
    //when
    loanService.approveLoan(1L);
  }

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.INSERT,
      value = "datasets/loan/should-approve-loan.yml"
  )
  @DisplayName("should throw exception if loan not found")
  void shouldThrowExceptionIfLoanNotFount() {
    //given
    var loanId = -1;

    //then
    var exception = assertThrows(LoanNotFoundException.class, () -> loanService.approveLoan(loanId));
    assertEquals("Loan with id = " + loanId + " does not exist", exception.getMessage());
  }

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.INSERT,
      value = "datasets/loan/should-approve-loan.yml"
  )
  @DisplayName("should not approve loan if user is banned")
  void shouldNotApproveLoanIfUserIsBanned() {
    //given
    var loanId = 1L;

    //when
    loanService.approveLoan(loanId);
  }

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.INSERT,
      value = "datasets/loan/should-return-all-approved-loans.yml"
  )
  @DisplayName("should return all approved loans")
  void shouldReturnAllApprovedLoans() {
    //when
    var allApprovedLoans = loanService.getAllApprovedLoans();

    //then
    assertEquals(3, allApprovedLoans.size());
  }

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.INSERT,
      value = "datasets/loan/should-not-return-approved-loans-if-does-not-exist.yml"
  )
  @DisplayName("should not return approved loans if does not exist")
  void shouldNotReturnApprovedLoansIfDoesNotExist() {
    //when
    var allApprovedLoans = loanService.getAllApprovedLoans();

    //then
    assertTrue(allApprovedLoans.isEmpty());
  }

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.INSERT,
      value = "datasets/loan/should-return-all-approved-loans-by-user-id.yml"
  )
  @DisplayName("should return all approved loans by user id")
  void shouldReturnAllApprovedLoansByUserId() {
    //when
    var allApprovedLoansByUserId = loanService.getAllUserApprovedLoans(1);

    //then
    assertEquals(1, allApprovedLoansByUserId.size());
  }

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.INSERT,
      value = "datasets/loan/should-not-return-approved-loans-by-user-id-if-does-not-exist.yml"
  )
  @DisplayName("should not return approved loans by personal id if does not exist")
  void shouldNotReturnApprovedLoansByPUserIdIfDoesNotExist() {
    //when
    var allApprovedLoansByUserId = loanService.getAllUserApprovedLoans(3);

    //then
    assertTrue(allApprovedLoansByUserId.isEmpty());
  }

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.INSERT
  )
  @ExpectedDataSet(value = "datasets/loan/should-apply-loan-expected.yml")
  @DisplayName("should apply loan")
  void shouldApplyLoan() {
    //given
    var applyLoanDto = new ApplyLoanRequestDto(LOAN_AMOUNT, TERM_DAYS, USER_NAME, USER_SURNAME, PERSONAL_ID);

    //when
    loanService.applyLoan(applyLoanDto);
  }

}