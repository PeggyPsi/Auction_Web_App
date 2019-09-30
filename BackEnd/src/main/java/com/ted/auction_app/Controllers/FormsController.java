/* The controller is implementing functions to handle HTTP requests.*/

package com.ted.auction_app.Controllers;

import com.ted.auction_app.Models.User.UserDTO;
import com.ted.auction_app.Models.User.UserDetailsRequest;
import com.ted.auction_app.Models.User.UserDetailsResponse;
import com.ted.auction_app.Services.Declarations.UserService;
import com.ted.auction_app.Utils.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FormsController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDetailsResponse> createUser( @Valid @RequestBody UserDetailsRequest userDetailsRequest )
    {
        // Copy user object to dto to share info through the API layers
        UserDTO userDTO = new UserDTO();

        // Copy properties from request model to DTO model
        BeanUtils.copyProperties(userDetailsRequest, userDTO);
        BeanUtils.copyProperties(userDetailsRequest.getLocation(), userDTO.getLocation());

        //Check database validation of user
        userService.userDBValid(userDTO);

        //Save the user in case of no errors
        //Create user
        UserDTO createdUser = userService.createUser(userDTO);

        // User object to be returned as a response
        UserDetailsResponse returnValue = new UserDetailsResponse();

        // Copy new created user's info in the return value
        BeanUtils.copyProperties(createdUser, returnValue);
        BeanUtils.copyProperties(createdUser.getLocation(), returnValue.getLocation());

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }
}

