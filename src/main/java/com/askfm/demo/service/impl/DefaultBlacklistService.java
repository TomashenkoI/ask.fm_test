package com.askfm.demo.service.impl;

import com.askfm.demo.exception.BlackListedUserException;
import com.askfm.demo.repository.BlacklistRepository;
import com.askfm.demo.service.BlacklistService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultBlacklistService implements BlacklistService {

  private final BlacklistRepository blacklistRepository;

  @Override
  public void checkIfNotBanned(int personalId) {
    blacklistRepository.findByPersonalId(personalId).ifPresent(user -> {
      throw new BlackListedUserException("User is banned, personalId = " + personalId);
    });
  }
}
