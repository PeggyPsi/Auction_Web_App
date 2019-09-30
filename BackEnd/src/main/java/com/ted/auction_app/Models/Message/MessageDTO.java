package com.ted.auction_app.Models.Message;

import com.ted.auction_app.Models.User.UserDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class MessageDTO {
    private Integer id;
    private UserDTO fromUser;
    private UserDTO toUser;
    private Date dateTime;
    private String message;
    private Boolean seen;
    private String fromRole;
    private Boolean deletedFrom;
    private Boolean deletedTo;

    public MessageDTO() {
        this.fromUser = new UserDTO();
        this.toUser = new UserDTO();
    }

    public MessageDTO(Integer id, Date dateTime, String message, Boolean seen, String fromRole, Boolean deletedFrom, Boolean deletedTo) {
        this.id = id;
        this.dateTime = dateTime;
        this.message = message;
        this.seen = seen;
        this.fromRole = fromRole;
        this.deletedFrom = deletedFrom;
        this.deletedTo = deletedTo;
        this.fromUser = new UserDTO();
        this.toUser = new UserDTO();
    }

    public MessageEntity copy2Entity(MessageDTO message) {
        MessageEntity messageEntity = new MessageEntity(
                message.id,
                message.dateTime,
                message.message,
                message.seen,
                message.fromRole,
                message.deletedFrom,
                message.deletedTo
        );
        // Copy from user
        BeanUtils.copyProperties(message.getFromUser(), messageEntity.getFromUser());
        BeanUtils.copyProperties(message.getFromUser().getLocation(), messageEntity.getFromUser().getLocation());
        // Copy to user
        BeanUtils.copyProperties(message.getToUser(), messageEntity.getToUser());
        BeanUtils.copyProperties(message.getToUser().getLocation(), messageEntity.getToUser().getLocation());

        return messageEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserDTO fromUser) {
        this.fromUser = fromUser;
    }

    public UserDTO getToUser() {
        return toUser;
    }

    public void setToUser(UserDTO toUser) {
        this.toUser = toUser;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String getFromRole() {
        return fromRole;
    }

    public void setFromRole(String fromRole) {
        this.fromRole = fromRole;
    }

    public Boolean getDeletedFrom() {
        return deletedFrom;
    }

    public void setDeletedFrom(Boolean deletedFrom) {
        this.deletedFrom = deletedFrom;
    }

    public Boolean getDeletedTo() {
        return deletedTo;
    }

    public void setDeletedTo(Boolean deletedTo) {
        this.deletedTo = deletedTo;
    }

    @Override
    public String toString() {
        System.out.println("MessageDTO{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", message='" + message + '\'' +
                ", seen=" + seen +
                ", fromRole='" + fromRole + '\'' +
                ", deletedFrom='" + deletedFrom + '\'' +
                ", deletedTo='" + deletedTo + '\'' +
                '}');
        fromUser.toString();
        toUser.toString();
        return null;
    }
}
