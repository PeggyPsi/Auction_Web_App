package com.ted.auction_app.Repositories;

import com.ted.auction_app.Models.Bid.BidEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BidRepository extends CrudRepository<BidEntity, Integer> {
    List<BidEntity> findAllByItem_Id(Integer itemId);

    @Query(value = "select * from bids b where b.item_id = ?1 order by b.amount desc limit ?2", nativeQuery = true)
    List<BidEntity> findBidsByLimit(Integer itemId, Integer maxNum);
}
