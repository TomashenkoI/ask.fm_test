package com.askfm.demo.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoanDto {
  private BigDecimal amount;
  private Integer termDays;
  private long userId;
  private Instant createdDate;
  private String countryCode;
  private Instant termEndDate;
  private boolean approved;
}
