package com.ted.auction_app.Repositories;

import com.ted.auction_app.Models.Rating.RatingEntity;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<RatingEntity, Integer> {

    RatingEntity findRatingEntityByRated_PublicIdAndWasRated_PublicId(String fromUser, String toUser);
}
