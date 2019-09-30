package com.ted.auction_app.Services.Declarations;

public interface RatingService {

    Boolean checkRatingExists(String forUser, String fromUser);
    void addRatingSeller(String sellerId, String fromUser, Integer rating);
    void addRatingBuyer(String buyerId, String fromUser, Integer rating);
}
