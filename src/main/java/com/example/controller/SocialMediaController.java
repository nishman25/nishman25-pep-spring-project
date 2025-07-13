package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
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

    private final MessageService messageService;
    private final AccountService accountService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        Message createdMessage = messageService.createMessage(message);
        if (createdMessage != null) {
            return ResponseEntity.ok(createdMessage);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId){
        Integer rowsDeleted = messageService.deleteMessage(messageId);
        if (rowsDeleted == 1){
            return ResponseEntity.ok(rowsDeleted); 
        } else {
            return ResponseEntity.status(200).body(null);
        }
        
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List <Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);      
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId){
        List <Message> userMessages = messageService.getAllMessagesByUser(accountId);
        return ResponseEntity.ok(userMessages);      
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessagesById(@PathVariable Integer messageId){
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(message);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer messageId, @RequestBody Map<String, String> updates) {
        String messageText = updates.get("messageText"); 
        Integer rowsUpdated = messageService.updateMessage(messageId, messageText);
        
        if (rowsUpdated > 0){
            return ResponseEntity.ok(rowsUpdated);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account createdAccount = accountService.registerAccount(account);
        if (createdAccount != null) {
            return ResponseEntity.ok(createdAccount);
        } else {
            return ResponseEntity.status(409).build();
        }
    }

}

/*
As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. 
The body will contain a representation of a JSON Account, but will not contain an accountId.

- The registration will be successful if and only if the username is not blank, 
the password is at least 4 characters long, and an Account with that username does not already exist. 
If all these conditions are met, the response body should contain a JSON of the Account, including its accountId. 
The response status should be 200 OK, which is the default. The new account should be persisted to the database.
- If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
- If the registration is not successful for some other reason, the response status should be 400. (Client error)
 */


