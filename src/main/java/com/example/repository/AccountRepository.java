package com.example.repository;

/**
 * AccountRepository provides data access methods for the Account entity using Spring Data JPA.
 * It extends JpaRepository to support standard CRUD operations and includes custom query 
 * methods to check for the existence of a username and to retrieve an account by username.
 */

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {

  public abstract boolean existsByUsername(String username);
  public abstract Optional<Account> findByUsername(String username);

}

