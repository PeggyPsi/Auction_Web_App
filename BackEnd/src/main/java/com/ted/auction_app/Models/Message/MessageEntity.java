package com.ted.auction_app.Models.Message;

import com.ted.auction_app.Models.User.UserEntity;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="messages")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "from_user", nullable = false)
    private UserEntity fromUser;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "to_user", nullable = false)
    private UserEntity toUser;

    @Column(name = "date_time")
    private Date dateTime;

    @Column(name = "message")
    private String message;

    @Column(name = "seen")
    private Boolean seen;

    @Column(name = "from_role")
    private String fromRole;

    @Column(name = "deleted_from")
    private Boolean deletedFrom;

    @Column(name = "deleted_to")
    private Boolean deletedTo;

    public MessageEntity() {
        fromUser = new UserEntity();
        toUser = new UserEntity();
    }

    public MessageEntity(Integer id, Date dateTime, String message, Boolean seen, String fromRole, Boolean deletedFrom, Boolean deletedTo) {
        this.id = id;
        this.dateTime = dateTime;
        this.message = message;
        this.seen = seen;
        this.fromRole = fromRole;
        this.deletedFrom = deletedFrom;
        this.deletedTo = deletedTo;
        this.toUser = new UserEntity();
        this.fromUser = new UserEntity();
    }

    public MessageDTO copy2DTO(MessageEntity message) {
        MessageDTO messageDTO = new MessageDTO(
                message.id,
                message.dateTime,
                message.message,
                message.seen,
                message.fromRole,
                message.deletedFrom,
                message.deletedTo
        );
        // Copy from user
        BeanUtils.copyProperties(message.getFromUser(), messageDTO.getFromUser());
        BeanUtils.copyProperties(message.getFromUser().getLocation(), messageDTO.getFromUser().getLocation());
        // Copy to user
        BeanUtils.copyProperties(message.getToUser(), messageDTO.getToUser());
        BeanUtils.copyProperties(message.getToUser().getLocation(), messageDTO.getToUser().getLocation());

        return messageDTO;


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
    }

    public UserEntity getToUser() {
        return toUser;
    }

    public void setToUser(UserEntity toUser) {
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
        System.out.println("MessageEntity{" +
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
