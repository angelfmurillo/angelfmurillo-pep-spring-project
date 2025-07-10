package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {

  public abstract boolean existsByUsername(String username);
  public abstract Optional<Account> findByUsername(String username);

}

