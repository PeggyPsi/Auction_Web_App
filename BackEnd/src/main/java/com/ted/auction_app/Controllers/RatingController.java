package com.ted.auction_app.Controllers;

import com.ted.auction_app.Errors.SuccessResponse;
import com.ted.auction_app.Services.Declarations.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RatingController {

    @Autowired
    RatingService ratingService;


    @RequestMapping(value = "/ratings/exist/for/{forUser}/from/{fromUser}", method = RequestMethod.GET)
    public ResponseEntity<?> ratingExists(@PathVariable("forUser") String forUser,
                                     @PathVariable("fromUser") String fromUser) {

        Boolean exists = ratingService.checkRatingExists(forUser, fromUser);

        return new ResponseEntity<>(new SuccessResponse(exists.toString()), HttpStatus.OK);
    }

    @RequestMapping(value = "/ratings/seller/{sellerId}/from/{fromUser}", method = RequestMethod.GET)
    public ResponseEntity<?> addSellerRating(@PathVariable("sellerId") String sellerId,
                                     @PathVariable("fromUser") String fromUser,
                                     @RequestParam("rating") Integer rating) {

        System.out.println(sellerId + fromUser + rating);

        ratingService.addRatingSeller(sellerId, fromUser, rating);

        return new ResponseEntity<>(new SuccessResponse("Rating placed successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/ratings/buyer/{buyerId}/from/{fromUser}", method = RequestMethod.GET)
    public ResponseEntity<?> addBuyerRating(@PathVariable("buyerId") String buyerId,
                                     @PathVariable("fromUser") String fromUser,
                                     @RequestParam("rating") Integer rating) {

        System.out.println(buyerId + fromUser + rating);

        ratingService.addRatingBuyer(buyerId, fromUser, rating);

        return new ResponseEntity<>(new SuccessResponse("Rating placed successfully"), HttpStatus.OK);
    }

}
