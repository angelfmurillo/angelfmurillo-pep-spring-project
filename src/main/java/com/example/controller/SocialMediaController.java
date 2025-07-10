package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
 public class SocialMediaController {

    @Autowired
    private AccountService acctService;

//    @Autowired
//    private MessageService msgService;


    //## 1: Our API should be able to process new User registrations.
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account acct){

        String username = acct.getUsername();
        String password = acct.getPassword();

        if (username.isBlank() || password.length() < 4){
            return ResponseEntity.badRequest().build();
        }

        Account createdAcct = acctService.registerUser(username, password);

        if (createdAcct != null){ return ResponseEntity.ok(createdAcct);}
        else return ResponseEntity.status(409).build();

    }


    

}
