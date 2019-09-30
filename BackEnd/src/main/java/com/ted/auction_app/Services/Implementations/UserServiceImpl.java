package com.ted.auction_app.Services.Implementations;

import com.ted.auction_app.Errors.ErrorResponse;
import com.ted.auction_app.Exceptions.UserAlreadyExists;
import com.ted.auction_app.Models.User.UserDTO;
import com.ted.auction_app.Models.User.UserEntity;
import com.ted.auction_app.Repositories.LocationRepository;
import com.ted.auction_app.Repositories.UserRepository;
import com.ted.auction_app.Services.Declarations.UserService;
import com.ted.auction_app.Utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO){
        // Create new user entity to save the user in the database
        UserEntity userEntity = new UserEntity();

        //Copy DTO's info in user entity model
        BeanUtils.copyProperties(userDTO, userEntity);
        BeanUtils.copyProperties(userDTO.getLocation(), userEntity.getLocation());

        // Update user's other info
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRole("SITE_USER");
        userEntity.setPublicId(utils.generateUserId(30));
        userEntity.setVerified(false);
        userEntity.setSellerRating(0);
        userEntity.setBuyerRating(0);

        // Save location info in database
        locationRepository.save(userEntity.getLocation());
        // Save user in the database. Use communication of service layer with Repository.
        UserEntity storedUser = userRepository.save(userEntity);

        // Copy created user in a DTO model and return it
        UserDTO returnValue = new UserDTO();
        BeanUtils.copyProperties(storedUser, returnValue);
        BeanUtils.copyProperties(storedUser.getLocation(), returnValue.getLocation());
        return returnValue;
    }

    @Override
    public boolean userDBValid(UserDTO userDTO) {
        // Checks user repository to find user with a specific username
        // Used to check whether there is already a user with that the provided username
        // Check also the uniqueness of the provided AFM
        Boolean userExists = userExists(userDTO);
        if (userExists) throw new UserAlreadyExists("Invalid username (db duplicate): " + userDTO.getUsername());
        return false;
    }

    @Override
    public boolean userExists(UserDTO userDTO) {
        // Checks user repository to find user with a specific username
        // Used to check whether there is already a user with that the provided username
        return userRepository.findByUsername(userDTO.getUsername())!=null;
    }

    // Get info about a specific user
    @Override
    public UserDTO userByUsername(String username){
        UserEntity userEntity = userRepository.findByUsername(username);
        //if user doesnt exist
        if (userEntity == null) return null;

        // Copy retrieved user in a DTO model and return it
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);
        // in case we are requesting for admin
        if (userEntity.getLocation() != null) BeanUtils.copyProperties(userEntity.getLocation(), userDTO.getLocation());

        return userDTO;
    }

    @Override
    public UserDTO userByPublicId(String publicId){
        UserEntity userEntity = userRepository.findByPublicId(publicId);
        //if user doesnt exist
        if (userEntity == null) return null;

        // Copy retrieved user in a DTO model and return it
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userEntity, userDTO);
        // in case we are requesting for admin
        if (userEntity.getLocation() != null) BeanUtils.copyProperties(userEntity.getLocation(), userDTO.getLocation());

        return userDTO;
    }

    @Override
    public UserDTO authenticateUser(String username, String password){
        // Find user in the database
        UserDTO user = userByUsername(username);

        // Check if user exists
        if(user == null) return null;

//        // Check if user has been verified from admin
//        // If not he cannot yet login
//        if(user.getVerified() != true) return null;

        // Check if database password and the provided one match each other
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) return user;

        // user could not be authenticated
        return null;
    }

    @Override
    public List<UserDTO> getAllUsers(){
        List<UserEntity> userEntities = userRepository.findAll();

        // no unverified registration requests
        if(userEntities.isEmpty()) {
            System.out.println("hey");
            return new ArrayList<>();
        }
        else{
            System.out.println("hoy");
            // Create list of DTOs
            List<UserDTO> userDTOs = new ArrayList<>();
            // iterate through the list of retrieved users from the database
            userEntities.forEach((userEntity) -> {
                // create new userDTO
                UserDTO userDTO = new UserDTO();

                // Copy user entity in the userDTO
                BeanUtils.copyProperties(userEntity, userDTO);
                if (userEntity.getLocation() != null) BeanUtils.copyProperties(userEntity.getLocation(), userDTO.getLocation());            //in case of admin

                // Append userDTO in return list
                userDTOs.add(userDTO);
            });
            return userDTOs;
        }
    }

    @Override
    public ResponseEntity<ErrorResponse> verifyUser(String public_id, Boolean status){
        // Search for user with specific public id in database
        UserEntity userEntity = userRepository.findByPublicId(public_id);

        if(userEntity == null){
            System.out.println("conflict");
            ErrorResponse error = new ErrorResponse("User with specific id not found", new ArrayList<>());
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
        else{
//            // check if user already verified
//            if(userEntity.getVerified()){
//                ErrorResponse error = new ErrorResponse("User's account is already accepted", new ArrayList<>());
//                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
//            }
//            else{
                // update verification value
                userEntity.setVerified(status);
                // save user back in database
                userRepository.save(userEntity);
                System.out.println("ok");
                ErrorResponse error = new ErrorResponse("User successfully verified", new ArrayList<>());
                return new ResponseEntity<>(error, HttpStatus.OK);
//            }
        }
    }
}
