package com.ted.auction_app.Models.Bid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ted.auction_app.Models.Item.ItemEntity;
import com.ted.auction_app.Models.User.UserEntity;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "bids")
public class BidEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnore
    private ItemEntity item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id", nullable = false)
    @JsonIgnore
    private UserEntity bidder;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "bid_time")
    private Date time;

    @Column(name = "win_status")
    private String winStatus;

    public BidEntity() {
        this.bidder = new UserEntity();
        this.item = new ItemEntity();
    }

    public BidEntity(Double amount, Date time, String winStatus) {
        this.amount = amount;
        this.time = time;
        this.winStatus = winStatus;
        this.bidder = new UserEntity();
        this.item = new ItemEntity();
    }

    public BidDTO copy2DTO(BidEntity bid) {
        BidDTO bidDTO = new BidDTO(bid.id, bid.amount, bid.time, bid.winStatus);
        // Copy bidder
        BeanUtils.copyProperties(bid.getBidder(), bidDTO.getBidder());
        BeanUtils.copyProperties(bid.getBidder().getLocation(), bidDTO.getBidder().getLocation());
        // Copy item
        bidDTO.setItem(bid.getItem().copy2DTO(bid.getItem()));
        return bidDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public UserEntity getBidder() {
        return bidder;
    }

    public void setBidder(UserEntity bidder) {
        this.bidder = bidder;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getWinStatus() {
        return winStatus;
    }

    public void setWinStatus(String winStatus) {
        this.winStatus = winStatus;
    }

    @Override
    public String toString() {
        System.out.println("BidEntity{" +
                "id=" + id +
                ", amount=" + amount +
                ", time=" + time +
                ", winStatus='" + winStatus + '\'' +
                '}');
        this.item.toString();
        this.bidder.toString();
        return null;
    }
}
