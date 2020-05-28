package com.askfm.demo.service.impl;


import com.askfm.demo.PostgresqlDbBaseTest;
import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.service.UserService;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.spring.api.DBRider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.askfm.demo.TestConstantHolder.LOAN_AMOUNT;
import static com.askfm.demo.TestConstantHolder.PERSONAL_ID;
import static com.askfm.demo.TestConstantHolder.TERM_DAYS;
import static com.askfm.demo.TestConstantHolder.USER_NAME;
import static com.askfm.demo.TestConstantHolder.USER_SURNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DBRider
@SpringBootTest
class DefaultUserServiceIntegrationTest extends PostgresqlDbBaseTest {

  @Autowired
  private UserService userService;

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.CLEAN_INSERT
  )
  @ExpectedDataSet(value = "datasets/user/should-register-user-expected.yml")
  @DisplayName("should register user")
  void shouldRegisterUser() {
    //given
    var applyLoanDto = new ApplyLoanRequestDto();
    applyLoanDto.setAmount(LOAN_AMOUNT);
    applyLoanDto.setName(USER_NAME);
    applyLoanDto.setSurname(USER_SURNAME);
    applyLoanDto.setTermDays(TERM_DAYS);
    applyLoanDto.setPersonalId(PERSONAL_ID);

    //when
    userService.registerUser(applyLoanDto);
  }

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.CLEAN_INSERT,
      value = "datasets/user/should-find-user-if-exist.yml"
  )
  @DisplayName("should return user if exist")
  void shouldReturnUserIfExist() {
    //when
    var userByPersonalId = userService.getUserByPersonalId(PERSONAL_ID);

    //then
    assertTrue(userByPersonalId.isPresent());
    assertEquals(1, userByPersonalId.get().getId());
  }

  @Test
  @DataSet(
      executeScriptsBefore = "db-cleanup.sql",
      strategy = SeedStrategy.INSERT,
      value = "datasets/user/should-not-find-user-if-does-not-exist.yml"
  )
  @DisplayName("should not return user if does not exist")
  void shouldNotReturnUserIfDoesNotExist() {
    //when
    var userByPersonalId = userService.getUserByPersonalId(PERSONAL_ID);

    //then
    assertFalse(userByPersonalId.isPresent());
  }

}