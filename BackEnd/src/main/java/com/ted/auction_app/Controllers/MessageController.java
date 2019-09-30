package com.ted.auction_app.Controllers;

import com.ted.auction_app.Errors.ErrorResponse;
import com.ted.auction_app.Errors.SuccessResponse;
import com.ted.auction_app.Models.Message.MessageDTO;
import com.ted.auction_app.Services.Declarations.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public ResponseEntity<?> saveMessage(@RequestBody MessageDTO message){
        // Save message
        messageService.saveMessage(message);

        return new ResponseEntity<>(new SuccessResponse("Message saved successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/messages/inbox/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> inbox(@PathVariable("userId") String userId){

        // Get messages
        List<MessageDTO> messages = messageService.getInbox(userId);

        if(messages.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No inbox messages", null), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @RequestMapping(value = "/messages/outbox/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> outbox(@PathVariable("userId") String userId){

        // Get messages
        List<MessageDTO> messages = messageService.getOutbox(userId);

        if(messages.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No outbox messages", null), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @RequestMapping(value = "/messages/{messageId}/seen", method = RequestMethod.GET)
    public ResponseEntity<?> seen(@PathVariable("messageId") Integer messageId){

        // Get messages
        messageService.setSeen(messageId);

        return new ResponseEntity<>(new SuccessResponse("Message has been seen"), HttpStatus.OK);
    }

    @RequestMapping(value = "/messages/{messageId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> seen(@PathVariable("messageId") Integer messageId,
                                  @RequestParam("for") String forUser){

        // Get messages
        messageService.deleteMessageFor(messageId, forUser);

        return new ResponseEntity<>(new SuccessResponse("Message has been deleted successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/messages/unseenNum/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> numUnseen(@PathVariable("userId") String userId){

        // Get messages
        Integer unseen = messageService.getUnseenNum(userId);

        return new ResponseEntity<>(new SuccessResponse(unseen.toString()), HttpStatus.OK);
    }
}
