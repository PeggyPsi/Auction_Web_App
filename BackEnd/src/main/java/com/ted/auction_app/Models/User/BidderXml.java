package com.ted.auction_app.Models.User;

import com.ted.auction_app.Models.Location.CountryXml;
import com.ted.auction_app.Models.Location.LocationXml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class BidderXml {

    private Integer bidderRating;

    private String username;

    private LocationXml location;

    private CountryXml country;

    public BidderXml() {
    }

    public BidderXml(Integer bidderRating, String username) {
        this.bidderRating = bidderRating;
        this.username = username;
        this.location = new LocationXml();
        this.country = new CountryXml();
    }

    @XmlAttribute(name = "Rating")
    public Integer getBidderRating() {
        return bidderRating;
    }

    public void setBidderRating(Integer bidderRating) {
        this.bidderRating = bidderRating;
    }

    @XmlAttribute(name = "UserID")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement(name = "Location")
    public LocationXml getLocation() {
        return location;
    }

    public void setLocation(LocationXml location) {
        this.location = location;
    }

    @XmlElement(name = "Country")
    public CountryXml getCountry() {
        return country;
    }

    public void setCountry(CountryXml country) {
        this.country = country;
    }
}
