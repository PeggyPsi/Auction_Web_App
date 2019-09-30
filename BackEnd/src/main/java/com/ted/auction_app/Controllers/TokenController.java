package com.ted.auction_app.Controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import com.ted.auction_app.Errors.ErrorResponse;
import com.ted.auction_app.Models.User.UserDTO;
import com.ted.auction_app.Models.User.UserDetailsRequest;
import com.ted.auction_app.Models.User.UserDetailsResponse;
import com.ted.auction_app.Services.Declarations.UserService;
import com.ted.auction_app.Utils.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// Class to acquire the jwt token for the authenticated user.
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TokenController {

    @Autowired
    UserService userService;

    // Here we are assuming that the reader know about the model classes in mvc architecture.
    // User is a model class having two parameters i.e. username and password. This class will be used to fetch the login credentials from the request.
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody Map<String, String> login) throws ServletException {

        String jwttoken;

        String username = login.get("username");
        String password = login.get("password");

        System.out.println(username);
        System.out.println(password);

        // Check if user exists
        UserDTO userDTO = userService.authenticateUser(username, password);
        if (userDTO == null){
            return new ResponseEntity<>(new ErrorResponse("Username or Password are incorrect", null), HttpStatus.FORBIDDEN);
        }
        //User not yet verified
        if(!userDTO.getVerified()) return new ResponseEntity<>(new ErrorResponse("You are not yet verified", null), HttpStatus.FORBIDDEN);

        // Creating JWT using the user credentials.
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("usr", username);
        claims.put("sub", "Authentication token");
        claims.put("iss", Constants.ISSUER);
        claims.put("rol", "Administrator, Developer");
        claims.put("iat", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        jwttoken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, Constants.SECRET_KEY).compact();
//        System.out.println("Returning the following token to the user= "+ jwttoken);

        UserDetailsResponse userResponse = new UserDetailsResponse();
        BeanUtils.copyProperties(userDTO, userResponse);
        BeanUtils.copyProperties(userDTO.getLocation(), userResponse.getLocation());
        userResponse.setJwtToken(jwttoken);
//        return new ResponseEntity<>(jwttoken, HttpStatus.OK);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
