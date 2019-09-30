package com.ted.auction_app.Models.Item;

import com.ted.auction_app.Models.Category.CategoryDTO;
import com.ted.auction_app.Models.Category.CategoryDetails;
import com.ted.auction_app.Models.Location.LocationDetails;
import com.ted.auction_app.Models.User.UserDetailsRequest;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

public class ItemDetailsRequest {

    private Integer id;

    @NotEmpty(message = "Not empty")
    private String name;

    @NotEmpty(message = "Not empty")
    private String description;

    @NotEmpty(message = "Not empty")
    private Double firstBid;

    @NotEmpty(message = "Not empty")
    private Date auctionStart;

    @NotEmpty(message = "Not empty")
    private Date auctionEnd;

    private Double buyOffer;

    @Valid
    private LocationDetails location;

    @NotEmpty(message = "Not empty")
    private UserDetailsRequest seller;

    @NotEmpty(message = "Not Empty")
    @Valid
    private Set<CategoryDetails> categories;

    public ItemDetailsRequest() {
    }

    public ItemDTO copy2DTO(ItemDetailsRequest item) {
        ItemDTO itemDTO = new ItemDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getFirstBid(),
                item.getAuctionStart(),
                item.getAuctionEnd(),
                item.getBuyOffer()
        );
        // Copy location
        BeanUtils.copyProperties(item.getLocation(), itemDTO.getLocation());
        // Copy User
        BeanUtils.copyProperties(item.getSeller(), itemDTO.getSeller());
        BeanUtils.copyProperties(item.getSeller().getLocation(), itemDTO.getSeller().getLocation());
        // Copy categories
        for(CategoryDetails categoryDetails: item.getCategories()) {
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(categoryDetails, categoryDTO);
            itemDTO.getCategories().add(categoryDTO);
        }

        return itemDTO;
    }

    public Double getBuyOffer() {
        return buyOffer;
    }

    public void setBuyOffer(Double buyOffer) {
        this.buyOffer = buyOffer;
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

    public LocationDetails getLocation() {
        return location;
    }

    public void setLocation(LocationDetails location) {
        this.location = location;
    }

    public UserDetailsRequest getSeller() {
        return seller;
    }

    public void setSeller(UserDetailsRequest seller) {
        this.seller = seller;
    }

    public Set<CategoryDetails> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDetails> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        System.out.println("ItemDetailsRequest{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", firstBid=" + firstBid +
                ", auctionStart=" + auctionStart +
                ", auctionEnd=" + auctionEnd +
                '}');
        this.location.toString();
        for ( CategoryDetails category : this.categories ) category.toString();
        this.seller.toString();
        return null;
    }
}
