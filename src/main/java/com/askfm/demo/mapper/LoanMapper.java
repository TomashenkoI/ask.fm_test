package com.askfm.demo.mapper;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.dto.LoanDto;
import com.askfm.demo.entity.LoanEntity;

import org.mapstruct.Mapper;

@Mapper
public interface LoanMapper {
  LoanEntity toEntity(ApplyLoanRequestDto applyLoanRequestDto);

  LoanDto toDto(LoanEntity loanEntity);
}
