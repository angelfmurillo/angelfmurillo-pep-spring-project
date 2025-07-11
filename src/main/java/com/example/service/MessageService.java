package com.example.service;

import java.util.List;

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
}
