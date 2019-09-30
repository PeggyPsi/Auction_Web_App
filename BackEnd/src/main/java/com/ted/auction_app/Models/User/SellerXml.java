package com.ted.auction_app.Models.User;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {
        "sellerRating",
        "username"
})
public class SellerXml {
    private Integer sellerRating;

    private String username;

    @XmlAttribute(name = "Rating")
    public Integer getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
    }

    @XmlAttribute(name = "UserID")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SellerXml() {
    }

    @Override
    public String toString() {
        System.out.println("SellerXml{" +
                "sellerRating=" + sellerRating +
                ", username='" + username + '\'' +
                '}');
        return null;
    }
}
