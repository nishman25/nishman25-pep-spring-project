package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}




/*

     * Sending an http request to DELETE localhost:8080/messages/1 (message exists)
     * 
     * Expected Response:
     *  Status Code: 200
     *  Response Body: count of rows modified (should only modify a single row)

    @Test
    public void deleteMessageGivenMessageIdMessageFound() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/9999"))
                .DELETE()
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);
        Integer actualResult = objectMapper.readValue(response.body().toString(), Integer.class);
        Assertions.assertTrue(actualResult.equals(1), "Expected to modify 1 row, but actually modified " + actualResult + " rows.");
    }
 
        public void deleteMessageGivenMessageIdMessageNotFound() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/messages/100"))
                .DELETE()
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);
        String actualResult = response.body().toString();
        Assertions.assertTrue(actualResult.equals(""), "Expected empty response body, but actually " + actualResult + ".");
    }
 */