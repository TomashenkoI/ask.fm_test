package com.askfm.demo.service;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.entity.User;

import java.util.Optional;

public interface UserService {
  Optional<User> getUserByPersonalId(long personalId);

  User registerUser(ApplyLoanRequestDto applyLoanRequestDto);
}
