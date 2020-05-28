package com.askfm.demo.mapper;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.entity.User;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
  User getUserData(ApplyLoanRequestDto applyLoanRequestDto);
}
