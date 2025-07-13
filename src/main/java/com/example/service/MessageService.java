package com.example.service;

import java.util.List;
import java.util.Optional;

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

    public Integer deleteMessage(Integer messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByUser(Integer accountId) {

        return messageRepository.findByPostedBy(accountId);
        
    }

    public Message getMessageById(Integer messageId) {
       Optional<Message> optionalMessage = messageRepository.findById(messageId);
       return optionalMessage.orElse(null);

    }

    public Integer updateMessage(Integer messageId, String messageText) {
        if (messageText == null || messageText.trim().isEmpty() || messageText.length() > 254) {
            return 0;
        } 
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()){
            Message message = messageOptional.get();
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }

        return 0;
    }

    


}

/*
 * As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{messageId}.

- The response body should contain a JSON representation of the message identified by the messageId. 
It is expected for the response body to simply be empty if there is no such message. The response status 
should always be 200, which is the default.
 * 
 */
