package com.askfm.demo.mapper;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.dto.LoanDto;
import com.askfm.demo.entity.LoanEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LoanMapper {
  LoanEntity toEntity(ApplyLoanRequestDto applyLoanRequestDto);

  @Mapping(target = "userId", source = "user.id")
  LoanDto toDto(LoanEntity loanEntity);
}
