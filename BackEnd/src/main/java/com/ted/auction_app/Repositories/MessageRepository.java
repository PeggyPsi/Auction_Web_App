package com.ted.auction_app.Repositories;

import com.ted.auction_app.Models.Message.MessageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageEntity, Integer> {
    List<MessageEntity> findAllByDeletedToAndToUser_PublicIdOrderByDateTimeDesc(Boolean deletedTo, String public_id);
    List<MessageEntity> findAllByDeletedFromAndFromUser_PublicIdOrderByDateTimeDesc(Boolean deletedFrom, String public_id);
    MessageEntity findMessageEntityById(Integer messageId);

    List<MessageEntity> findAllByToUser_PublicIdAndSeen(String userId, Boolean seen);
}
