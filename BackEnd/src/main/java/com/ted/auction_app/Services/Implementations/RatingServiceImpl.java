package com.ted.auction_app.Services.Implementations;

import com.ted.auction_app.Models.Rating.RatingEntity;
import com.ted.auction_app.Models.User.UserEntity;
import com.ted.auction_app.Repositories.RatingRepository;
import com.ted.auction_app.Repositories.UserRepository;
import com.ted.auction_app.Services.Declarations.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Boolean checkRatingExists(String forUser, String fromUser) {
        RatingEntity ratingEntity = ratingRepository.findRatingEntityByRated_PublicIdAndWasRated_PublicId(fromUser, forUser);
        if(ratingEntity == null) return false;
        else return true;
    }

    @Override
    public void addRatingSeller(String sellerId, String fromUserId, Integer rating){
        UserEntity seller = userRepository.findByPublicId(sellerId);
        UserEntity fromUser = userRepository.findByPublicId(fromUserId);

        // Update ratings table
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setRated(fromUser);
        ratingEntity.setWasRated(seller);
        ratingEntity.setRating(rating);
        ratingRepository.save(ratingEntity);

        // Update user seller_rating
        seller.setSellerRating(seller.getSellerRating()+rating);
        userRepository.save(seller);
    }

    @Override
    public void addRatingBuyer(String buyerId, String fromUserId, Integer rating){
        UserEntity buyer = userRepository.findByPublicId(buyerId);
        UserEntity fromUser = userRepository.findByPublicId(fromUserId);

        // Update ratings table
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setRated(fromUser);
        ratingEntity.setWasRated(buyer);
        ratingEntity.setRating(rating);
        ratingRepository.save(ratingEntity);

        // Update user seller_rating
        buyer.setBuyerRating(buyer.getSellerRating()+rating);
        userRepository.save(buyer);
    }
}
