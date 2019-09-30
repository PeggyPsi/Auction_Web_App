// Implements a class for user with many information to be shared among layers
// of rest api feature communication.

package com.ted.auction_app.Models.User;

import com.ted.auction_app.Models.Location.LocationDTO;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private Integer id;
    private String publicId;
    private String username;
    private String role;
    private String password;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String afm;
    private Boolean verified;
    private Integer buyerRating;
    private Integer sellerRating;
    private LocationDTO location;

    public UserDTO() {
        this.location = new LocationDTO();          // specified initialization for BeanUtils copy properties -> deep copy
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    @Override
    public String toString() {
        System.out.println("UserDTO{" +
                "id=" + id +
                ", publicId='" + publicId + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", afm='" + afm + '\'' +
                ", verified=" + verified +
                ", buyerRating=" + buyerRating +
                ", sellerRating=" + sellerRating +
                '}');
        this.location.toString();
        return null;
    }
}
