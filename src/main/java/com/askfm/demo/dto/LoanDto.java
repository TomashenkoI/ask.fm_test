package com.askfm.demo.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {
  private BigDecimal amount;
  private Integer term;
  private String name;
  private String surname;
  private long userId;
  private Instant createdDate;
  private Instant approvedDate;
}