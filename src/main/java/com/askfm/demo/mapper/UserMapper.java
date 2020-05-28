package com.askfm.demo.mapper;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.entity.UserEntity;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
  UserEntity getUserData(ApplyLoanRequestDto applyLoanRequestDto);
}
