// Implements a class for user's location info with many information to be shared among layers
// of rest api feature communication.

package com.ted.auction_app.Models.Location;

import java.io.Serializable;

public class LocationDTO implements Serializable {

    private Integer id;
    private String location;
    private Float latitude;
    private Float longitude;
    private String country;

    public LocationDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        System.out.println("LocationDTO{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                '}');
        return null;
    }
}
