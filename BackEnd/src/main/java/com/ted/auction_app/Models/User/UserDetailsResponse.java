// This specific class is going to be used to return info about a user when someone requests

package com.ted.auction_app.Models.User;

import com.ted.auction_app.Models.Location.LocationDetails;

public class UserDetailsResponse {

    private Integer id;
    private String publicId;
    private String username;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String afm;
    private String role;
    private Integer buyerRating;
    private Integer sellerRating;
    private LocationDetails location;
    private String jwtToken;
    private Boolean verified;

    public UserDetailsResponse() {
        this.location = new LocationDetails(); // specified initialization for BeanUtils copy properties -> deep copy
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public Integer getBuyerRating() {
        return buyerRating;
    }

    public void setBuyerRating(Integer buyerRating) {
        this.buyerRating = buyerRating;
    }

    public Integer getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(Integer sellerRating) {
        this.sellerRating = sellerRating;
    }

    public LocationDetails getLocation() {
        return location;
    }

    public void setLocation(LocationDetails location) {
        this.location = location;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
