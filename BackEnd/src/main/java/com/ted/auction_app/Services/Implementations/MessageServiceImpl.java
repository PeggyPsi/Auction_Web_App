package com.ted.auction_app.Services.Implementations;

import com.ted.auction_app.Models.Message.MessageDTO;
import com.ted.auction_app.Models.Message.MessageEntity;
import com.ted.auction_app.Repositories.MessageRepository;
import com.ted.auction_app.Services.Declarations.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public void saveMessage(MessageDTO message) {
        // Copy to Message entity
        MessageEntity messageEntity = message.copy2Entity(message);

        //Save message
        messageRepository.save(messageEntity);
    }

    @Override
    public List<MessageDTO> getInbox(String userId){
        // Get messages from database
        List<MessageEntity> messageEntities = messageRepository.findAllByDeletedToAndToUser_PublicIdOrderByDateTimeDesc(false, userId);

        // Copy to DTO
        List<MessageDTO> messages = new ArrayList<>();
        for(MessageEntity messageEntity: messageEntities) {
            messages.add(messageEntity.copy2DTO(messageEntity));
        }

        return messages;
    }

    @Override
    public List<MessageDTO> getOutbox(String userId) {
        // Get messages from database
        List<MessageEntity> messageEntities = messageRepository.findAllByDeletedFromAndFromUser_PublicIdOrderByDateTimeDesc(false, userId);

        // Copy to DTO
        List<MessageDTO> messages = new ArrayList<>();
        for(MessageEntity messageEntity: messageEntities) {
            messages.add(messageEntity.copy2DTO(messageEntity));
        }

        return messages;
    }

    @Override
    public void setSeen(Integer messageId) {
        MessageEntity message = messageRepository.findMessageEntityById(messageId);
        message.setSeen(true);
        messageRepository.save(message);
    }

    @Override
    public void deleteMessageFor(Integer messageId, String forUser){
        // Retrieve message from database
        MessageEntity message = messageRepository.findMessageEntityById(messageId);
        if(forUser.equals("receiver")) message.setDeletedTo(true);
        else if(forUser.equals("sender")) message.setDeletedFrom(true);
        messageRepository.save(message);
    }

    @Override
    public Integer getUnseenNum(String userId){
        List<MessageEntity> messages = messageRepository.findAllByToUser_PublicIdAndSeen(userId, false);
        return messages.size();
    }
}
