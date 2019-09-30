package com.ted.auction_app.Models.Item;

import com.ted.auction_app.Models.Bid.BidEntity;
import com.ted.auction_app.Models.Bid.BidXml;
import com.ted.auction_app.Models.Category.CategoryDTO;
import com.ted.auction_app.Models.Category.CategoryEntity;
import com.ted.auction_app.Models.Category.CategoryXml;
import com.ted.auction_app.Models.Location.LocationEntity;
import com.ted.auction_app.Models.User.BidderXml;
import com.ted.auction_app.Models.User.UserEntity;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="items")
public class ItemEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "first_bid", nullable = false)
    private Double firstBid;

    @Column(name = "current_bid")
    private Double currentBid;

    @Column(name = "auction_start", nullable = false)
    private Date auctionStart;

    @Column(name = "auction_end", nullable = false)
    private Date auctionEnd;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private UserEntity seller;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "buyer_id")
    private UserEntity buyer;

    @OneToMany(mappedBy = "item")
    private Set<BidEntity> bids;

    @Column(name = "buy_price")
    private Double buyPrice;

    @Column(name = "buy_offer")
    private Double buyOffer;

    @Column(name = "no_bids", nullable = false)
    private Integer noBids;

    @Column(name = "ended")
    private Boolean ended;

    @Column(name = "active")
    private Boolean active;

    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)              // orphan to delete location entry
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable( name = "item_category", joinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<CategoryEntity> categories;

    public ItemEntity() {
        this.location = new LocationEntity();
        this.categories = new HashSet<>();
        this.seller = new UserEntity();
        this.buyer = new UserEntity();
        this.bids = new HashSet<>();
    }

    public ItemEntity(String name, String description, Double firstBid, Double currentBid, Date auctionStart, Date auctionEnd, Double buyPrice, Integer noBids, Integer id, Boolean ended, Boolean active, Double buyOffer) {
        this.name = name;
        this.description = description;
        this.firstBid = firstBid;
        this.currentBid = currentBid;
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.buyPrice = buyPrice;
        this.noBids = noBids;
        this.id = id;
        this.location = new LocationEntity();
        this.categories = new HashSet<>();
        this.seller = new UserEntity();
        this.buyer = new UserEntity();
        this.bids = new HashSet<>();
        this.ended = ended;
        this.active = active;
        this.buyOffer = buyOffer;
    }

    public ItemDTO copy2DTO(ItemEntity item) {
        ItemDTO itemDTO = new ItemDTO(
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.getFirstBid(),
            item.getCurrentBid(),
            item.getAuctionStart(),
            item.getAuctionEnd(),
            item.getBuyPrice(),
            item.getNoBids(),
                item.getEnded(),
                item.getActive(),
                item.getBuyOffer()
        );
        // Copy location
        BeanUtils.copyProperties(item.getLocation(), itemDTO.getLocation());
        // Copy User
        BeanUtils.copyProperties(item.getSeller(), itemDTO.getSeller());
        BeanUtils.copyProperties(item.getSeller().getLocation(), itemDTO.getSeller().getLocation());
        // Copy buyer
        if(item.getBuyer() != null){
            BeanUtils.copyProperties(item.getBuyer(), itemDTO.getBuyer());
            BeanUtils.copyProperties(item.getBuyer().getLocation(), itemDTO.getBuyer().getLocation());
        }
        // Copy categories
        for(CategoryEntity categoryEntity: item.getCategories()) {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(categoryEntity, categoryDTO);
            itemDTO.getCategories().add(categoryDTO);
        }

        return itemDTO;
    }

    public ItemXml copy2Xml(ItemEntity item) {
        ItemXml itemXml = new ItemXml(
                item.getId(),
                item.getName(),
                item.getCurrentBid(),
                item.getFirstBid(),
                item.getNoBids(),
                item.getAuctionStart(),
                item.getAuctionEnd(),
                item.getDescription(),
                item.getBuyOffer()
        );
        // Copy location
        BeanUtils.copyProperties(item.getLocation(), itemXml.getLocation());
        BeanUtils.copyProperties(item.getLocation(), itemXml.getCountry());
        // Copy User
        BeanUtils.copyProperties(item.getSeller(), itemXml.getSeller());
        // Copy categories
        for(CategoryEntity categoryEntity: item.getCategories()) {
            CategoryXml categoryXml = new CategoryXml();
            BeanUtils.copyProperties(categoryEntity, categoryXml);
            itemXml.getCategories().add(categoryXml);
        }

        // Copy bids
        for(BidEntity bidEntity: bids) {
            // Create bidder xml entity
            BidderXml bidder = new BidderXml(bidEntity.getBidder().getBuyerRating(), bidEntity.getBidder().getUsername());
            // Copy bidder location
            BeanUtils.copyProperties(bidEntity.getBidder().getLocation(), bidder.getLocation());
            BeanUtils.copyProperties(bidEntity.getBidder().getLocation(), bidder.getCountry());

            // create new xml bid entity
            BidXml bidXml = new BidXml(bidEntity.getTime(), bidEntity.getAmount());
            bidXml.setBidder(bidder);

            // Copy bid to array of xml bids
            itemXml.getBids().add(bidXml);
        }

        return itemXml;
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

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
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

    public UserEntity getSeller() {
        return seller;
    }

    public void setSeller(UserEntity seller) {
        this.seller = seller;
    }

    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
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

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public Set<BidEntity> getBids() {
        return bids;
    }

    public void setBids(Set<BidEntity> bids) {
        this.bids = bids;
    }

    @Override
    public String toString() {
        System.out.println("ItemEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", firstBid=" + firstBid +
                ", currentBid=" + currentBid +
                ", auctionStart=" + auctionStart +
                ", auctionEnd=" + auctionEnd +
                ", buyPrice=" + buyPrice +
                ", auctionEnd=" + auctionEnd +
                ", ended=" + ended +
                ", active=" + active +
                ", buyOffer=" + buyOffer +
                '}');
        for ( CategoryEntity category : this.categories ) category.toString();
        for ( BidEntity bid : this.bids ) bid.toString();
        this.seller.toString();
        this.buyer.toString();
        this.location.toString();

        return null;
    }
}
