package com.askfm.demo.service;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.entity.UserEntity;

import java.util.Optional;

public interface UserService {
  Optional<UserEntity> getUserByPersonalId(long personalId);

  UserEntity registerUser(ApplyLoanRequestDto applyLoanRequestDto);
}
