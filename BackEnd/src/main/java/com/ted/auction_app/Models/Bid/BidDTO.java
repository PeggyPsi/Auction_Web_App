package com.ted.auction_app.Models.Bid;

import com.ted.auction_app.Models.Item.ItemDTO;
import com.ted.auction_app.Models.User.UserDTO;
import org.springframework.beans.BeanUtils;

import java.util.Date;

public class BidDTO {

    private Integer id;
    private ItemDTO item;
    private UserDTO bidder;
    private Double amount;
    private Date time;
    private String winStatus;

    public BidDTO() {
        this.bidder = new UserDTO();
        this.item = new ItemDTO();
    }

    public BidDTO(Integer id, Double amount, Date time, String winStatus) {
        this.id = id;
        this.amount = amount;
        this.time = time;
        this.winStatus = winStatus;
        this.bidder = new UserDTO();
        this.item = new ItemDTO();
    }

    public BidEntity copy2Entity(BidDTO bid) {
        BidEntity bidEntity = new BidEntity(bid.amount, bid.time, bid.winStatus);
        // Copy bidder
        BeanUtils.copyProperties(bid.getBidder(), bidEntity.getBidder());
        BeanUtils.copyProperties(bid.getBidder().getLocation(), bidEntity.getBidder().getLocation());
        // Copy item
        bidEntity.setItem(bid.getItem().copy2Entity(bid.getItem()));
        return bidEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public UserDTO getBidder() {
        return bidder;
    }

    public void setBidder(UserDTO bidder) {
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
        System.out.println("BidDTO{" +
                "id=" + id +
                ", amount=" + amount +
                ", time=" + time +
                ", winStatus='" + winStatus + '\'' +
                '}');
        item.toString();
        bidder.toString();
        return null;
    }
}

