package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

/**
 * MessageService provides business logic for handling operations related to messages 
 * in the social media application. It supports adding new messages, retrieving all 
 * messages or messages by ID, deleting and updating existing messages, and retrieving 
 * messages posted by a specific user. This service layer interacts with the 
 * MessageRepository to perform CRUD operations against the underlying database.
 */
@Service
public class MessageService {

    @Autowired
    private MessageRepository msgRepo;

    public Message addMessage(String msgText, int postedBy, long timePosted){

        Message newMsg = new Message(postedBy, msgText, timePosted);
        return msgRepo.save(newMsg);
    }

    public List<Message> getAllMessages(){

        return msgRepo.findAll();
    }

    public Message getMessageById(int msgId){

        return msgRepo.findById(msgId).orElse(null);
    }

    public Message deleteMsgById(int msgId){

        Optional<Message> optionalMsg = msgRepo.findById(msgId);

        if (optionalMsg.isPresent()){
            msgRepo.deleteById(msgId);
            return optionalMsg.get();
        }

        return null;
    }

    public boolean updateMessage(int messageId, String newText){

        Optional<Message> optMsg = msgRepo.findById(messageId);
        boolean rowsUpdated = false;
        Message msg = null;

        if (optMsg.isPresent()){
            msg = optMsg.get();
            msg.setMessageText(newText);
            msgRepo.save(msg);
            rowsUpdated = true;
        }
        
        return rowsUpdated;
    }

    public List<Message> getMessagesByAccountId(int accountId){

        return msgRepo.findByPostedBy(accountId);

    } 
}
