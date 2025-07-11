package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * SocialMediaController serves as the main REST controller for handling all user account and message-related 
 * operations in the Spring social media application. It provides endpoints for registering new users, logging in, 
 * posting new messages, retrieving all or individual messages, updating or deleting messages, and fetching 
 * messages by user ID. This controller interacts with the AccountService and MessageService layers to process
 * incoming HTTP requests, validate input data, and return appropriate HTTP responses in JSON format.
 */

@RestController
public class SocialMediaController {

        @Autowired
        private AccountService acctService;

        @Autowired
        private MessageService msgService;

        //## 1: API method to process new User registrations.
        @PostMapping("/register")
        public ResponseEntity<Account> registerAccountHandler(@RequestBody Account acct) {

                String username = acct.getUsername();
                String password = acct.getPassword();
                Account createdAcct = null;

                if (username.isBlank() || username == null || password.length() < 4 || password == null) {
                        return ResponseEntity.badRequest().build();
                }

                createdAcct = acctService.registerUser(username, password);
                if (createdAcct != null) {
                        return ResponseEntity.ok(createdAcct);
                } else
                        return ResponseEntity.status(409).build();
        }

        //## 2: API method to process User logins.
        @PostMapping("/login")
        public ResponseEntity<Account> loginHandler(@RequestBody Account loginReq) {

                String userName = loginReq.getUsername();
                String passwd = loginReq.getPassword();

                Account acctLoggedIn = acctService.loginUser(userName, passwd);

                if (acctLoggedIn != null) {
                        return ResponseEntity.ok(acctLoggedIn);
                } else
                        return ResponseEntity.status(401).build();
        }

        //## 3: API method process the creation of new messages.
        @PostMapping("/messages")
        public ResponseEntity<Message> postMessagesHandler(@RequestBody Message userMessage) {

                String msg = userMessage.getMessageText();
                int postedById = userMessage.getPostedBy();
                long timePosted = userMessage.getTimePostedEpoch();
                Message addedMsg = null;

                int messageMaxLength = 255;

                if (msg.isBlank() || msg.length() > messageMaxLength || !acctService.accountExists(postedById)) {
                        return ResponseEntity.badRequest().build();
                }

                addedMsg = msgService.addMessage(msg, postedById, timePosted);

                if (addedMsg != null) {
                        return ResponseEntity.ok(addedMsg);
                } else
                        return ResponseEntity.badRequest().build();
        }

        //## 4: API method to retrieve all messages.
        @GetMapping("/messages")
        public ResponseEntity<List<Message>> getMessagesHandler() {

                List<Message> allMsgs = msgService.getAllMessages();
                return ResponseEntity.ok(allMsgs);

        }

        //## 5: API method to retrieve a message by its ID.
        @GetMapping("/messages/{message_id}")
        public ResponseEntity<Message> getMessageByIdHandler(@PathVariable("message_id") int msgId) {

                Message foundMsg = msgService.getMessageById(msgId);

                if (foundMsg != null) {
                        return ResponseEntity.ok(foundMsg);
                } else
                        return ResponseEntity.ok().build();
        }

        //## 6: API method to delete a message identified by a message ID.
        @DeleteMapping("/messages/{message_id}")
        public ResponseEntity<Integer> deleteMessageByIdHandler(@PathVariable("message_id") int msgId) {

                Message deletedMsg = msgService.deleteMsgById(msgId);

                if (deletedMsg != null) {
                        return ResponseEntity.ok(1);
                } else
                        return ResponseEntity.ok().build();
        }

        //## 7: API methid to update a message text identified by a message ID.
        @PatchMapping("messages/{message_id}")
        public ResponseEntity<Integer> patchMessageByIdHandler(@PathVariable("message_id") int msgId,
                        @RequestBody Message msg) {

                String msgText = msg.getMessageText();
                int msgMaxLength = 255;
                boolean rowsUpdated = false;

                if (msgText == null || msgText.isBlank() || msgText.length() >= msgMaxLength) {
                        return ResponseEntity.badRequest().build();
                }

                rowsUpdated = msgService.updateMessage(msgId, msgText);

                if (rowsUpdated) {
                        return ResponseEntity.ok(1);
                } else
                        return ResponseEntity.badRequest().build();
        }

        //## 8: API method to retrieve all messages written by a particular user.
        @GetMapping("/accounts/{accountId}/messages")
        public ResponseEntity<List<Message>> getMessagesByAccountIdHandler(@PathVariable("accountId") int accountId) {

                List<Message> userMessages = msgService.getMessagesByAccountId(accountId);
                return ResponseEntity.ok(userMessages);
        }
}