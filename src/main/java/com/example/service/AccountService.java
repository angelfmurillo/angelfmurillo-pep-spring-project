package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {


    @Autowired
    private AccountRepository acctRepository;

    public Account registerUser(String username, String password){

        if (acctRepository.existsByUsername(username)) { return null;}

        Account newAccount = new Account(username, password);
        return acctRepository.save(newAccount);
    } 


}
