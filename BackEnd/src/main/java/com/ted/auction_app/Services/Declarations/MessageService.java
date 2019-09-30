package com.ted.auction_app.Services.Declarations;

import com.ted.auction_app.Models.Message.MessageDTO;

import java.util.List;

public interface MessageService {
    void saveMessage(MessageDTO message);
    List<MessageDTO> getInbox(String userId);
    List<MessageDTO> getOutbox(String userId);
    void setSeen(Integer messageId);
    void deleteMessageFor(Integer messageId, String forUser);
    Integer getUnseenNum(String userId);
}
