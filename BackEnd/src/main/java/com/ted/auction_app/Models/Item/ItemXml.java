package com.ted.auction_app.Models.Item;

import com.ted.auction_app.Models.Bid.BidXml;
import com.ted.auction_app.Models.Category.CategoryXml;
import com.ted.auction_app.Models.Location.CountryXml;
import com.ted.auction_app.Models.Location.LocationXml;
import com.ted.auction_app.Models.User.SellerXml;

import javax.xml.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.*;

@XmlRootElement(name = "Item")
@XmlType(propOrder = {
        "name",
        "categories",
        "currentBid",
        "buyOffer",
        "firstBid",
        "noBids",
        "bids",
        "location",
        "country",
        "auctionStart",
        "auctionEnd",
        "seller",
        "description"
})
public class ItemXml {

    private Integer id;

    private String name;

    private Set<CategoryXml> categories;

    private Double currentBid;

    private Double firstBid;

    private Double buyOffer;

    private Integer noBids;

    private LocationXml location;

    private CountryXml country;

    private Date auctionStart;

    private Date auctionEnd;

    private SellerXml seller;

    private String description;

    private List<BidXml> bids;

    public ItemXml() {
    }

    public ItemXml(Integer id, String name, Double currentBid, Double firstBid, Integer noBids, Date auctionStart, Date auctionEnd, String description, Double buyOffer) {
        this.id = id;
        this.name = name;
        this.categories = new HashSet<>();
        this.currentBid = currentBid;
        this.firstBid = firstBid;
        this.noBids = noBids;
        this.location = new LocationXml();
        this.country = new CountryXml();
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.seller = new SellerXml();
        this.description = description;
        this.bids = new ArrayList<>();
        this.buyOffer = buyOffer;
    }

    @XmlElement(name = "Buy_Price")
    public Double getBuyOffer() {
        return buyOffer;
    }

    public void setBuyOffer(Double buyOffer) {
        this.buyOffer = buyOffer;
    }

    @XmlAttribute(name = "ItemID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "Category")
    public Set<CategoryXml> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryXml> categories) {
        this.categories = categories;
    }

    @XmlElement(name = "Currently")
    public String getCurrentBid() {
        return "$"+currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    @XmlElement(name = "First_Bid")
    public String getFirstBid() {
        return "$"+firstBid;
    }

    public void setFirstBid(Double firstBid) {
        this.firstBid = firstBid;
    }

    @XmlElement(name = "Number_of_Bids")
    public Integer getNoBids() {
        return noBids;
    }

    public void setNoBids(Integer noBids) {
        this.noBids = noBids;
    }

    @XmlElement(name = "Location")
    public LocationXml getLocation() {
        return location;
    }

    @XmlElement(name = "Country")
    public CountryXml getCountry() {
        return country;
    }

    public void setCountry(CountryXml country) {
        this.country = country;
    }

    public void setLocation(LocationXml location) {
        this.location = location;
    }

    @XmlElement(name = "Started")
    public String getAuctionStart() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdfDate.format(auctionStart);
        return strDate;
    }

    public void setAuctionStart(Date auctionStart) {
        this.auctionStart = auctionStart;
    }

    @XmlElement(name = "Ends")
    public String getAuctionEnd() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdfDate.format(auctionEnd);
        return strDate;
    }

    public void setAuctionEnd(Date auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    @XmlElement(name = "Seller")
    public SellerXml getSeller() {
        return seller;
    }

    public void setSeller(SellerXml seller) {
        this.seller = seller;
    }

    @XmlElement(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElementWrapper(name="Bids")
    @XmlElement(name="Bid")
    public List<BidXml> getBids() {
        return bids;
    }

    public void setBids(List<BidXml> bids) {
        this.bids = bids;
    }

    @Override
    public String toString() {
        System.out.println("ItemXml{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", firstBid=" + firstBid +
                ", currentBid=" + currentBid +
                ", auctionStart=" + auctionStart +
                ", auctionEnd=" + auctionEnd +
                ", noBids=" + noBids +
                '}');
        for ( CategoryXml category : this.categories ) category.toString();
        this.seller.toString();
        this.location.toString();
        this.country.toString();
        return null;
    }
}
