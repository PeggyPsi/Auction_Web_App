// Represents the object inside the database

package com.ted.auction_app.Models.User;

import com.ted.auction_app.Models.Location.LocationEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {                   // TODO serialization is used to convert objects to byte-streams to be saved in the disk

//    @Id               // TODO check in registration or in other parts whether there is a problem
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "public_id", nullable = false)
    private String publicId;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "username", unique=true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fname", nullable = false)
    private String fname;

    @Column(name = "lname", nullable = false)
    private String lname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "afm", nullable = false)
    private String afm;

    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @Column(name = "buyer_rating")
    private Integer buyerRating;

    @Column(name = "seller_rating")
    private Integer sellerRating;

    @OneToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    public UserEntity() {
        this.location = new LocationEntity();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }
}
