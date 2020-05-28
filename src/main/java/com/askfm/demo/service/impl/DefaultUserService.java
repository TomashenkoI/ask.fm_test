package com.askfm.demo.service.impl;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.entity.User;
import com.askfm.demo.mapper.UserMapper;
import com.askfm.demo.repository.UserRepository;
import com.askfm.demo.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public Optional<User> getUserByPersonalId(long personalId) {
    return userRepository.findById(personalId);
  }

  @Override
  @Transactional
  public User registerUser(ApplyLoanRequestDto applyLoanRequestDto) {
    var user = userMapper.getUserData(applyLoanRequestDto);
    return userRepository.save(user);
  }
}
