package com.ted.auction_app.Controllers;

import com.ted.auction_app.Errors.ErrorResponse;
import com.ted.auction_app.Errors.SuccessResponse;
import com.ted.auction_app.Models.Bid.BidDTO;
import com.ted.auction_app.Services.Declarations.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BidController {

    @Autowired
    BidService bidService;

    @RequestMapping(value = "/bids", method = RequestMethod.POST)
    public ResponseEntity<?> newBid(@RequestBody BidDTO bid,
                                    @RequestParam(value = "bought") Boolean bought) {
        // Send to service to be saved
        bidService.addBid(bid, bought);

        return new ResponseEntity<>(new SuccessResponse("Your bid was added successfully!"), HttpStatus.OK);
    }

    @RequestMapping(value = "/bids/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<?> getBids(@PathVariable("itemId") Integer itemId,
                                     @RequestParam("maxNum") Integer maxNum) {

        List<BidDTO> bids = bidService.getBids(itemId, maxNum);

        if(bids.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No bids have been placed.", null), HttpStatus.NOT_FOUND);

//        for(BidDTO bidDTO: bids) bidDTO.toString();

        return new ResponseEntity<>(bids, HttpStatus.OK);
    }
}
