package com.ted.auction_app.Models.Item;

import com.ted.auction_app.Models.Category.CategoryDetails;
import com.ted.auction_app.Models.Image.ImageDetails;
import com.ted.auction_app.Models.Location.LocationDetails;
import com.ted.auction_app.Models.User.UserDetailsResponse;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ItemDetailsResponse {

    private Integer id;
    private String name;
    private String description;
    private Double firstBid;
    private Double currentBid;
    private Date auctionStart;
    private Date auctionEnd;
    private UserDetailsResponse seller;
    private UserDetailsResponse buyer;
    private Double buyPrice;
    private Double buyOffer;
    private Integer noBids;
    private Boolean ended;
    private Boolean active;
    private LocationDetails location;
    private Set<CategoryDetails> categories;
    private Set<ImageDetails> images;

    public ItemDetailsResponse() {
    }

    public ItemDetailsResponse(Integer id, String name, String description, Double firstBid, Double currentBid, Date auctionStart, Date auctionEnd, Double buyPrice, Integer noBids, Boolean ended, Boolean active, Double buyOffer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.firstBid = firstBid;
        this.currentBid = currentBid;
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.buyPrice = buyPrice;
        this.noBids = noBids;
        this.seller = new UserDetailsResponse();
        this.buyer = new UserDetailsResponse();
        this.location = new LocationDetails();
        this.categories = new HashSet<>();
        this.images = new HashSet<>();
        this.ended = ended;
        this.active = active;
        this.buyOffer = buyOffer;
    }

    public Double getBuyOffer() {
        return buyOffer;
    }

    public void setBuyOffer(Double buyOffer) {
        this.buyOffer = buyOffer;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getEnded() {
        return ended;
    }

    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFirstBid() {
        return firstBid;
    }

    public void setFirstBid(Double firstBid) {
        this.firstBid = firstBid;
    }

    public Double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    public Date getAuctionStart() {
        return auctionStart;
    }

    public void setAuctionStart(Date auctionStart) {
        this.auctionStart = auctionStart;
    }

    public Date getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Date auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    public UserDetailsResponse getBuyer() {
        return buyer;
    }

    public void setBuyer(UserDetailsResponse buyer) {
        this.buyer = buyer;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getNoBids() {
        return noBids;
    }

    public void setNoBids(Integer noBids) {
        this.noBids = noBids;
    }

    public UserDetailsResponse getSeller() {
        return seller;
    }

    public void setSeller(UserDetailsResponse seller) {
        this.seller = seller;
    }

    public LocationDetails getLocation() {
        return location;
    }

    public void setLocation(LocationDetails location) {
        this.location = location;
    }

    public Set<CategoryDetails> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDetails> categories) {
        this.categories = categories;
    }

    public Set<ImageDetails> getImages() {
        return images;
    }

    public void setImages(Set<ImageDetails> images) {
        this.images = images;
    }
}
