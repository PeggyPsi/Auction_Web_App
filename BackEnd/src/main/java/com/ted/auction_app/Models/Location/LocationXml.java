package com.ted.auction_app.Models.Location;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class LocationXml {

    private String location;
    private Float latitude;
    private Float longitude;

    @XmlValue
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlAttribute(name = "Latitude")
    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    @XmlAttribute(name = "Longitude")
    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }


    public LocationXml() {
    }

    @Override
    public String toString() {
        System.out.println("LocationXml{" +
                "location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}');
        return null;
    }
}
