package com.ted.auction_app.Services.Implementations;

import com.ted.auction_app.Models.Bid.BidDTO;
import com.ted.auction_app.Models.Bid.BidEntity;
import com.ted.auction_app.Repositories.BidRepository;
import com.ted.auction_app.Repositories.ItemRepository;
import com.ted.auction_app.Services.Declarations.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    BidRepository bidRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public void addBid(BidDTO bid, Boolean bought) {
        // Copy DTO to Entity
        BidEntity bidEntity = bid.copy2Entity(bid);

        System.out.println(bidEntity);

        if(bought) {
            bidEntity.setWinStatus("YES");
            bidEntity.getItem().setCurrentBid(bidEntity.getAmount());
            bidEntity.getItem().setNoBids(bidEntity.getItem().getNoBids()+1);
            bidEntity.getItem().setBuyer(bidEntity.getBidder());
            bidEntity.getItem().setEnded(true);
            bidEntity.getItem().setBuyPrice(bidEntity.getAmount());
        }
        else {
            bidEntity.setWinStatus("NO");
            bidEntity.getItem().setCurrentBid(bidEntity.getAmount());
            bidEntity.getItem().setNoBids(bidEntity.getItem().getNoBids()+1);
            bidEntity.getItem().setBuyer(null);
        }

//        System.out.println(bidEntity.getItem().getBuyer().getUsername());

        // save bid
        BidEntity savedBid = bidRepository.save(bidEntity);

        // Save updated item
        itemRepository.save(bidEntity.getItem());
    }

    @Override
    public List<BidDTO> getBids(Integer itemId, Integer maxNum) {
        List<BidEntity> bidEntities;
        // if maxNum = 0 -> return all bids
        if(maxNum == 0) bidEntities = bidRepository.findAllByItem_Id(itemId);
        // else return maximum number
        else bidEntities = bidRepository.findBidsByLimit(itemId, maxNum);

        // Copy entities
        List<BidDTO> bidDTOS = new ArrayList<>();
        for(BidEntity bidEntity: bidEntities){
            BidDTO bidDTO = bidEntity.copy2DTO(bidEntity);
            bidDTOS.add(bidDTO);
        }

        return bidDTOS;
    }
}
