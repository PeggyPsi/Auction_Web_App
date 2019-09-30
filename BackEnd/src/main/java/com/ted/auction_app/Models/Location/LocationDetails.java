// This class is used to retrieve information from the user
// for example during the part of signing up.
// It also works as a response object.

package com.ted.auction_app.Models.Location;

import javax.validation.constraints.NotEmpty;

public class LocationDetails {

    private Integer id;

    @NotEmpty(message = "{location.notEmpty}")
    private String location;

    private Float latitude;

    private Float longitude;

    @NotEmpty(message = "{country.notEmpty}")
    private String country;

    public LocationDetails() {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        System.out.println("LocationDetails{" +
                "id='" + id + '\'' +
                "location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                '}');
        return null;
    }
}
