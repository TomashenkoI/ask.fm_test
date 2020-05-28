package com.askfm.demo.repository;

import com.askfm.demo.entity.Loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
  List<Loan> findAllByApprovedIs(boolean approved);
  List<Loan> findAllByApprovedIsTrueAndUserIdIs(long userId);
}
