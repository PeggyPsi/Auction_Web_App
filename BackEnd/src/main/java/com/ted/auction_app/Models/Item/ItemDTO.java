package com.ted.auction_app.Models.Item;

import com.ted.auction_app.Models.Category.CategoryDTO;
import com.ted.auction_app.Models.Category.CategoryDetails;
import com.ted.auction_app.Models.Category.CategoryEntity;
import com.ted.auction_app.Models.Location.LocationDTO;
import com.ted.auction_app.Models.User.UserDTO;
import org.springframework.beans.BeanUtils;

import java.util.*;

public class ItemDTO {

    private Integer id;
    private String name;
    private String description;
    private Double firstBid;
    private Double currentBid;
    private Date auctionStart;
    private Date auctionEnd;
    private UserDTO seller;
    private UserDTO buyer;
    private Double buyPrice;
    private Double buyOffer;
    private Integer noBids;
    private Boolean ended;
    private Boolean active;
    private LocationDTO location;
    private Set<CategoryDTO> categories;

    public ItemDTO() {
        this.categories = new HashSet<>();
        this.location = new LocationDTO();
        this.seller = new UserDTO();
        this.buyer = new UserDTO();
    }

    public ItemDTO(Integer id, String name, String description, Double firstBid, Date auctionStart, Date auctionEnd, Double buyOffer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.firstBid = firstBid;
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.categories = new HashSet<>();
        this.location = new LocationDTO();
        this.seller = new UserDTO();
        this.buyer = new UserDTO();
        this.buyOffer = buyOffer;
    }

    public ItemDTO(Integer id, String name, String description, Double firstBid, Double currentBid, Date auctionStart, Date auctionEnd, Double buyPrice, Integer noBids, Boolean ended, Boolean active, Double buyOffer) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.firstBid = firstBid;
        this.currentBid = currentBid;
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.buyPrice = buyPrice;
        this.noBids = noBids;
        this.categories = new HashSet<>();
        this.location = new LocationDTO();
        this.seller = new UserDTO();
        this.buyer = new UserDTO();
        this.ended = ended;
        this.active = active;
        this.buyOffer = buyOffer;
    }

    public ItemDetailsResponse copy2DetailsRes(ItemDTO item) {
        ItemDetailsResponse itemDetailsResponse = new ItemDetailsResponse(
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
        BeanUtils.copyProperties(item.getLocation(), itemDetailsResponse.getLocation());
        // Copy seller
        BeanUtils.copyProperties(item.getSeller(), itemDetailsResponse.getSeller());
        BeanUtils.copyProperties(item.getSeller().getLocation(), itemDetailsResponse.getSeller().getLocation());
        // Copy buyer
        if(item.getBuyer() != null){
            BeanUtils.copyProperties(item.getBuyer(), itemDetailsResponse.getBuyer());
            BeanUtils.copyProperties(item.getBuyer().getLocation(), itemDetailsResponse.getBuyer().getLocation());
        }
        // Copy categories
        for(CategoryDTO categoryDTO: item.getCategories()) {
            CategoryDetails categoryDetails = new CategoryDetails();
            BeanUtils.copyProperties(categoryDTO, categoryDetails);
            itemDetailsResponse.getCategories().add(categoryDetails);
        }

        return itemDetailsResponse;
    }

    public ItemEntity copy2Entity(ItemDTO item){
        ItemEntity itemEntity = new ItemEntity(
                item.getName(),
                item.getDescription(),
                item.getFirstBid(),
                item.getCurrentBid(),
                item.getAuctionStart(),
                item.getAuctionEnd(),
                item.getBuyPrice(),
                item.getNoBids(),
                item.getId(),
                item.getEnded(),
                item.getActive(),
                item.getBuyOffer()
        );
        // Copy location
        BeanUtils.copyProperties(item.getLocation(), itemEntity.getLocation());
        // Copy seller
        BeanUtils.copyProperties(item.getSeller(), itemEntity.getSeller());
        BeanUtils.copyProperties(item.getSeller().getLocation(), itemEntity.getSeller().getLocation());
        // Copy buyer
        if(item.getSeller() != null){
            BeanUtils.copyProperties(item.getBuyer(), itemEntity.getBuyer());
            BeanUtils.copyProperties(item.getBuyer().getLocation(), itemEntity.getBuyer().getLocation());
        }
        // Copy categories
        for(CategoryDTO categoryDTO: item.getCategories()) {
            CategoryEntity categoryEntity = new CategoryEntity();
            BeanUtils.copyProperties(categoryDTO, categoryEntity);
            itemEntity.getCategories().add(categoryEntity);
        }

        return itemEntity;
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

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
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

    public UserDTO getSeller() {
        return seller;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

    public UserDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(UserDTO buyer) {
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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    @Override
    public String toString() {
        System.out.println("ItemDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", firstBid=" + firstBid +
                ", currentBid=" + currentBid +
                ", auctionStart=" + auctionStart +
                ", auctionEnd=" + auctionEnd +
                ", buyPrice=" + buyPrice +
                ", noBids=" + noBids +
                ", ended=" + ended +
                ", active=" + active +
                ", buyOffer=" + buyOffer +
                '}');
        for ( CategoryDTO category : this.categories ) category.toString();
        this.seller.toString();
        this.buyer.toString();
        this.location.toString();
        return null;
    }
}
