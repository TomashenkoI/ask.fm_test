package com.askfm.demo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplyLoanRequestDto {
  private BigDecimal amount;
  private int termDays;
  private String name;
  private String surname;
  private int personalId;
}
