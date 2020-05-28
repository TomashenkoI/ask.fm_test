package com.askfm.demo.entity;

import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "loan")
@EqualsAndHashCode(exclude = {"user"})
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_seq")
  @SequenceGenerator(name = "loan_seq", sequenceName = "loan_seq", allocationSize = 1)
  private Long id;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Column(name = "term", nullable = false)
  private Integer term; //number of days

  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @Column(name = "term_end_date")
  private Instant termEndDate;

  @Column(name = "created_date", nullable = false)
  @CreationTimestamp
  private Instant createdDate;

  private boolean approved;

}
