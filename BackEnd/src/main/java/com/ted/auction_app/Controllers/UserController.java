package com.ted.auction_app.Controllers;

import com.ted.auction_app.Errors.ErrorResponse;
import com.ted.auction_app.Models.User.UserDTO;
import com.ted.auction_app.Models.User.UserDetailsResponse;
import com.ted.auction_app.Services.Declarations.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users/username/{username}", method = RequestMethod.GET)
    public UserDetailsResponse getUserByUsername(@PathVariable String username){
        System.out.println(username);

        UserDTO userDTO = userService.userByUsername(username);

        // Check if user exists
        if (userDTO == null) return null;

        // Copy retrieved user DTO in a UserDetailsResponse model and return it
        UserDetailsResponse userResponse = new UserDetailsResponse();
        BeanUtils.copyProperties(userDTO, userResponse);
        // in case we are requesting for admin
        if (userDTO.getLocation() != null) BeanUtils.copyProperties(userDTO.getLocation(), userResponse.getLocation());

        return userResponse;
    }

    @RequestMapping(value = "/users/id/{id}", method = RequestMethod.GET)
    public UserDetailsResponse getUserById(@PathVariable String id){
        UserDTO userDTO = userService.userByPublicId(id);

        // Check if user exists
        if (userDTO == null) return null;

        // Copy retrieved user DTO in a UserDetailsResponse model and return it
        UserDetailsResponse userResponse = new UserDetailsResponse();
        BeanUtils.copyProperties(userDTO, userResponse);
        // in case we are requesting for admin
        if (userDTO.getLocation() != null) BeanUtils.copyProperties(userDTO.getLocation(), userResponse.getLocation());

        return userResponse;
    }

    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public List<UserDetailsResponse> getUsers(){
        System.out.println("hey");
        List<UserDTO> userDTOs =  userService.getAllUsers();

        if(userDTOs.isEmpty()){
            System.out.println("empty");
            return new ArrayList<>();
        }
        else {
            System.out.println("not empty");
            // Create list of UserDetailsResponse
            List<UserDetailsResponse> userDetailsResponses = new ArrayList<>();
            // iterate through the list of retrieved users from the database
            userDTOs.forEach((userDTO) -> {
                // create new UserDetailsResponse
                UserDetailsResponse userDetailsResponse = new UserDetailsResponse();

                // Copy user entity in the UserDetailsResponse
                BeanUtils.copyProperties(userDTO, userDetailsResponse);
                if (userDTO.getLocation() != null) BeanUtils.copyProperties(userDTO.getLocation(), userDetailsResponse.getLocation());              //in case of admin

                // Append UserDetailsResponse in return list
                userDetailsResponses.add(userDetailsResponse);
            });

            return userDetailsResponses;
        }
    }

    @RequestMapping(value = "/users/verify", method = RequestMethod.PUT)
    public ResponseEntity<ErrorResponse> verifyUser(@RequestParam("id") String public_id,
                                                    @RequestParam("status") Boolean status){
        return userService.verifyUser(public_id, status);
    }
}
