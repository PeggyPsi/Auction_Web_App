package com.ted.auction_app.Repositories;

import com.ted.auction_app.Models.User.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
//    List<UserEntity> findAllByVerified(Boolean status);
    List<UserEntity> findAll();
    UserEntity findByPublicId(String public_id);
}
