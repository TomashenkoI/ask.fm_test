package com.askfm.demo.repository;

import com.askfm.demo.entity.BlacklistEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlacklistRepository extends JpaRepository<BlacklistEntity, Integer> {
  Optional<BlacklistEntity> findByPersonalId(int personalId);
}
