package com.ted.auction_app.Services.Declarations;

import com.ted.auction_app.Errors.ErrorResponse;
import com.ted.auction_app.Models.User.UserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);
    boolean userDBValid(UserDTO userDTO);
    boolean userExists(UserDTO user);
    UserDTO userByUsername(String username);
    UserDTO userByPublicId(String publicId);
    UserDTO authenticateUser(String username, String password);
    List<UserDTO> getAllUsers();
    ResponseEntity<ErrorResponse> verifyUser(String public_id, Boolean status);
}
