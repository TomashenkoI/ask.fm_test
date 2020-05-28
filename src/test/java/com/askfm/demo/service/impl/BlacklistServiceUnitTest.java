package com.askfm.demo.service.impl;

import com.askfm.demo.entity.BlacklistEntity;
import com.askfm.demo.exception.BlackListedUserException;
import com.askfm.demo.repository.BlacklistRepository;
import com.askfm.demo.service.BlacklistService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.askfm.demo.TestConstantHolder.PERSONAL_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BlacklistServiceUnitTest {

  private final BlacklistRepository mockBlacklistRepository = mock(BlacklistRepository.class);
  private final BlacklistService applyLoanRequestBannedUserValidator = new DefaultBlacklistService(mockBlacklistRepository);

  @Test
  @DisplayName("should throw exception if user is banned")
  void shouldThrowExceptionIfUserIsBanned() {
    //given
    when(mockBlacklistRepository.findByPersonalId(anyInt())).thenReturn(Optional.of(new BlacklistEntity()));

    //then
    var exception = assertThrows(
        BlackListedUserException.class,
        () -> applyLoanRequestBannedUserValidator.checkIfNotBanned(PERSONAL_ID)
    );
    assertEquals("User is banned, personalId = " + PERSONAL_ID, exception.getMessage());
  }

  @Test
  @DisplayName("should not throw exception if user is not banned")
  void shouldNotThrowExceptionIfUserIsNotBanned() {
    //given
    when(mockBlacklistRepository.findByPersonalId(anyInt())).thenReturn(Optional.empty());

    //then
    assertDoesNotThrow(() -> applyLoanRequestBannedUserValidator.checkIfNotBanned(PERSONAL_ID));
  }
}