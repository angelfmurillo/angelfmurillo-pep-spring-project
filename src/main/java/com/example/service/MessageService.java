package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

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
}
