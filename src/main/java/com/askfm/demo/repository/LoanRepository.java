package com.askfm.demo.repository;

import com.askfm.demo.entity.LoanEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
  List<LoanEntity> findAllByApprovedIs(boolean approved);

  List<LoanEntity> findAllByApprovedIsTrueAndUserIdIs(long userId);
}
