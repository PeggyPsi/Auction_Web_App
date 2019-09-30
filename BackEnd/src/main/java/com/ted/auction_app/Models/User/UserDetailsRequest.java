// Used as an object form user registration + form validation

package com.ted.auction_app.Models.User;

import com.ted.auction_app.CustomAnnotations.FieldMatch;
import com.ted.auction_app.Models.Location.LocationDetails;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "pwConfirm", message = "{password.confirm}")
public class UserDetailsRequest {

    @NotEmpty(message = "{username.notEmpty}")
//    @Size(min = 2, max = 20, message = "{username.size}")
    private String username;

    @NotEmpty(message = "{password.notEmpty}")
    @Size(min = 5, message = "{password.size}")
    private String password;

    @NotEmpty(message = "{pwConfirm.notEmpty}")
    private String pwConfirm;

    @NotEmpty(message = "{fname.notEmpty}")
    private String fname;

    @NotEmpty(message = "{lname.notEmpty}")
    private String lname;

    @NotEmpty(message = "{email.notEmpty}")
    @Email(message = "{email.valid}")
    private String email;

    @NotEmpty(message = "{phone.notEmpty}")
    @Size(min = 10, max = 10, message = "{phone.size}")
    private String phone;

    @NotEmpty(message = "{afm.notEmpty}")
    @Size(min = 11, max = 11, message = "{afm.size}")
    private String afm;

    @Valid
    private LocationDetails location;

    public UserDetailsRequest() {
    }

    public String getPwConfirm() {
        return pwConfirm;
    }

    public void setPwConfirm(String pwConfirm) {
        this.pwConfirm = pwConfirm;
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

    public LocationDetails getLocation() {
        return location;
    }

    public void setLocation(LocationDetails location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return username + location.getCountry();
    }


}
