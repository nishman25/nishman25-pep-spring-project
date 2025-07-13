package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountService accountService;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    public Message createMessage(Message message) {
        // Conditions to check before a message can be created
        if (message.getMessageText() == null ||
            message.getMessageText().trim().isEmpty() ||
            message.getMessageText().length() > 254 ||
            !accountService.accountExists(message.getPostedBy())) {
                return null;
            }
            
        return messageRepository.save(message);  
        
    }
}
