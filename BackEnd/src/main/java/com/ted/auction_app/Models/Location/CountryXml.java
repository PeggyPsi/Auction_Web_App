package com.ted.auction_app.Models.Location;

import javax.xml.bind.annotation.XmlValue;

public class CountryXml {

    private String country;

    public CountryXml() {
    }

    @XmlValue
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        System.out.println("CountryXml{" +
                "country='" + country + '\'' +
                '}');
        return null;
    }
}
