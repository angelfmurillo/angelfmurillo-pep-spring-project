package com.example.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

/**
 * AccountService contains the business logic related to user account operations 
 * in the social media application. It handles user registration by checking for 
 * duplicate usernames, processes user login by validating credentials, and verifies 
 * the existence of accounts by ID. This service communicates with the AccountRepository 
 * to perform necessary database operations.
 */

@Service
public class AccountService {


    @Autowired
    private AccountRepository acctRepository;

    public Account registerUser(String username, String password){

        if (acctRepository.existsByUsername(username)) { return null;}

        Account newAccount = new Account(username, password);
        return acctRepository.save(newAccount);
    } 

    public Account loginUser(String username, String password){

        Optional<Account> optAccount = acctRepository.findByUsername(username);

        if (optAccount.isPresent() && optAccount.get().getPassword().equals(password)) {
            return optAccount.get();
        }
        return null;
    }

    public boolean accountExists(int id){

       return acctRepository.existsById(id);
    }

}
