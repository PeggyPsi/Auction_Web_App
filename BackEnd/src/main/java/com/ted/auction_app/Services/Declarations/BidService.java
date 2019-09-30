package com.ted.auction_app.Services.Declarations;

import com.ted.auction_app.Models.Bid.BidDTO;

import java.util.List;

public interface BidService {
    void addBid(BidDTO bid, Boolean bought);
    List<BidDTO> getBids(Integer itemId, Integer maxNum);
}
