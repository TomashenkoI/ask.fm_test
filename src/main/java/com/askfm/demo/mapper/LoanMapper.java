package com.askfm.demo.mapper;

import com.askfm.demo.dto.ApplyLoanRequestDto;
import com.askfm.demo.dto.LoanDto;
import com.askfm.demo.entity.Loan;

import org.mapstruct.Mapper;

@Mapper
public interface LoanMapper {
  Loan toEntity(ApplyLoanRequestDto applyLoanRequestDto);

  LoanDto toDto(Loan loan);
}
