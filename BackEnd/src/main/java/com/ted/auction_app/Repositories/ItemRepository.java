package com.ted.auction_app.Repositories;

import com.ted.auction_app.Models.Item.ItemEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<ItemEntity, Integer> {
    ItemEntity findItemEntityById(Integer id);
    void removeItemEntityById(Integer id);
    List<ItemEntity> findAllByOrderByName();
    List<ItemEntity> findAllByOrderById();


    @Query(value = "select * from items i left join locations l on l.id=i.location_id WHERE MATCH(description)\n" +
            "AGAINST(?1 IN NATURAL LANGUAGE MODE) or match(name) against(?1 IN NATURAL LANGUAGE MODE)\n" +
            "or match(l.country) against(?1 IN NATURAL LANGUAGE MODE) or match(l.location) against(?1 IN NATURAL LANGUAGE MODE) and i.active=1 or \n"+
            "(select count(*) from item_category ic left join categories c on ic.category_id=c.id where ic.item_id=i.id and match(c.name) against(?1 IN NATURAL LANGUAGE MODE))>0", nativeQuery = true)
    List<ItemEntity> findByFreeText(String text);


    //// SELLER

        // General
        List<ItemEntity> findAllBySeller_PublicIdOrderByName(String sellerId);

        //Category
        @Query(value = "select * from items i inner join item_category ic on ic.item_id=i.id where ic.category_id=?2 and i.seller_id=?1", nativeQuery = true)
        List<ItemEntity> findAllBySeller_PublicIdAndCategory_IdOrderByName(String sellerId, Integer categoryId);

        //Price
        @Query(value = "select * from items i where i.seller_id=?1 and i.current_bid>=?2", nativeQuery = true)
        List<ItemEntity> findItemsBySellerAndPriceAtLeast(String sellerId,Integer min);
        @Query(value = "select * from items i where i.seller_id=?1 and i.current_bid<=?2", nativeQuery = true)
        List<ItemEntity> findItemsBySellerAndPriceAtMost(String sellerId,Integer max);
        @Query(value = "select * from items i where i.seller_id=?1 and i.current_bid>=?2 and i.current_bid<=?3", nativeQuery = true)
        List<ItemEntity> findItemsBySellerAndPriceRange(String sellerId,Integer min, Integer max);

        // Price and category
        @Query(value = "select * from items i inner join item_category ic on ic.item_id=i.id where i.seller_id=?1 and ic.category_id=?2 and i.current_bid>=?3", nativeQuery = true)
        List<ItemEntity> findItemsBySellerAndCategoryAndPriceAtLeast(String sellerId, Integer categoryId, Integer min);
        @Query(value = "select * from items i inner join item_category ic on ic.item_id=i.id where i.seller_id=?1 and ic.category_id=?2 and i.current_bid<=?3", nativeQuery = true)
        List<ItemEntity> findItemsBySellerAndCategoryAndPriceAtMost(String sellerId, Integer categoryId, Integer max);
        @Query(value = "select * from items i inner join item_category ic on ic.item_id=i.id where i.seller_id=?1 and ic.category_id=?2 and i.current_bid>=?3 and i.current_bid<=?4", nativeQuery = true)
        List<ItemEntity> findItemsBySellerAndCategoryAndPriceRange(String sellerId, Integer categoryId, Integer min, Integer max);

    //// BIDDER

        // ALL (BID)
        @Query(value = "select distinct * from items i join (select item_id from bids b where b.bidder_id=?1) b on b.item_id=i.id", nativeQuery = true)
        List<ItemEntity> findAllItemsByBidder(String bidderId);

            // category
            @Query(value = "select distinct * from items i join (select item_id from item_category ic where ic.category_id=?2) ic on ic.item_id=i.id join (select item_id from bids b where b.bidder_id=?1) b on b.item_id=i.id", nativeQuery = true)
            List<ItemEntity> findAllItemsByBidderAndCategory(String bidderId, Integer categoryId);

            // price
            @Query(value = "select distinct * from items i join (select item_id from bids b where b.bidder_id=?1) b on b.item_id=i.id where i.current_bid>=?2", nativeQuery = true)
            List<ItemEntity> findAllItemsByBidderAndPriceAtLeast(String bidderId, Integer min);
            @Query(value = "select distinct * from items i join (select item_id from bids b where b.bidder_id=?1) b on b.item_id=i.id where i.current_bid<=?2", nativeQuery = true)
            List<ItemEntity> findAllItemsByBidderAndPriceAtMost(String bidderId, Integer min);
            @Query(value = "select distinct * from items i join (select item_id from bids b where b.bidder_id=?1) b on b.item_id=i.id where i.current_bid>=?2 and i.current_bid<=3", nativeQuery = true)
            List<ItemEntity> findAllItemsByBidderAndPriceRange(String bidderId, Integer min, Integer max);

            // Category and price
            @Query(value = "select distinct * from items i join (select item_id from item_category ic where ic.category_id=?2) ic on ic.item_id=i.id join (select item_id from bids b where b.bidder_id=?1) b on b.item_id=i.id where i.current_bid>=?3", nativeQuery = true)
            List<ItemEntity> findAllItemsByBidderAndCategoryAndPriceAtLeast(String bidderId, Integer categoryId, Integer min);
            @Query(value = "select distinct * from items i join (select item_id from item_category ic where ic.category_id=?2) ic on ic.item_id=i.id join (select item_id from bids b where b.bidder_id=?1) b on b.item_id=i.id where i.current_bid<=?3", nativeQuery = true)
            List<ItemEntity> findAllItemsByBidderAndCategoryAndPriceAtMost(String bidderId, Integer categoryId, Integer max);
            @Query(value = "select distinct * from items i join (select item_id from item_category ic where ic.category_id=?2) ic on ic.item_id=i.id join (select item_id from bids b where b.bidder_id=?1) b on b.item_id=i.id where i.current_bid>=?3 and i.current_bid<=4", nativeQuery = true)
            List<ItemEntity> findAllItemsByBidderAndCategoryAndPriceRange(String bidderId, Integer categoryId, Integer min, Integer max);

        // WON
        @Query(value = "select distinct * from items i join (select item_id from bids b where b.bidder_id=?1 and b.win_status=\"YES\") b on b.item_id=i.id", nativeQuery = true)
        List<ItemEntity> findWonItemsByBidder(String bidderId);

            // Category
            @Query(value = "select distinct * from items i join (select item_id from item_category ic where ic.category_id=?2) ic on ic.item_id=i.id join (select item_id from bids b where b.bidder_id=?1 and b.win_status=\"YES\") b on b.item_id=i.id", nativeQuery = true)
            List<ItemEntity> findWonItemsByBidderAndCategory(String bidderId, Integer categoryId);

            // price
            @Query(value = "select distinct * from items i join (select item_id from bids b where b.bidder_id=?1 and b.win_status=\"YES\") b on b.item_id=i.id where i.current_bid>=?2", nativeQuery = true)
            List<ItemEntity> findWonItemsByBidderAndPriceAtLeast(String bidderId, Integer min);
            @Query(value = "select distinct * from items i join (select item_id from bids b where b.bidder_id=?1 and b.win_status=\"YES\") b on b.item_id=i.id where i.current_bid<=?2", nativeQuery = true)
            List<ItemEntity> findWonItemsByBidderAndPriceAtMost(String bidderId, Integer min);
            @Query(value = "select distinct * from items i join (select item_id from bids b where b.bidder_id=?1 and b.win_status=\"YES\") b on b.item_id=i.id where i.current_bid>=?2 and i.current_bid<=3", nativeQuery = true)
            List<ItemEntity> findWonItemsByBidderAndPriceRange(String bidderId, Integer min, Integer max);

            // Category and price
            @Query(value = "select distinct * from items i join (select item_id from item_category ic where ic.category_id=?2) ic on ic.item_id=i.id join (select item_id from bids b where b.bidder_id=?1 and b.win_status=\"YES\") b on b.item_id=i.id where i.current_bid>=?3", nativeQuery = true)
            List<ItemEntity> findWonItemsByBidderAndCategoryAndPriceAtLeast(String bidderId, Integer categoryId, Integer min);
            @Query(value = "select distinct * from items i join (select item_id from item_category ic where ic.category_id=?2) ic on ic.item_id=i.id join (select item_id from bids b where b.bidder_id=?1 and b.win_status=\"YES\") b on b.item_id=i.id where i.current_bid<=?3", nativeQuery = true)
            List<ItemEntity> findWonItemsByBidderAndCategoryAndPriceAtMost(String bidderId, Integer categoryId, Integer max);
            @Query(value = "select distinct * from items i join (select item_id from item_category ic where ic.category_id=?2) ic on ic.item_id=i.id join (select item_id from bids b where b.bidder_id=?1 and b.win_status=\"YES\") b on b.item_id=i.id where i.current_bid>=?3 and i.current_bid<=4", nativeQuery = true)
            List<ItemEntity> findWonItemsByBidderAndCategoryAndPriceRange(String bidderId, Integer categoryId, Integer min, Integer max);

    // Price
    @Query(value = "select * from items i where i.current_bid<=?1", nativeQuery = true)
    List<ItemEntity> findItemsByPriceAtMost(Integer max);
    @Query(value = "select * from items i where i.current_bid>=?1", nativeQuery = true)
    List<ItemEntity> findItemsByPriceAtLeast(Integer min);
    @Query(value = "select * from items i where i.current_bid>=?1 and i.current_bid<=?2", nativeQuery = true)
    List<ItemEntity> findItemsByPriceRange(Integer min, Integer max);


    // Price And Category
    @Query(value = "select * from items i inner join item_category ic on ic.item_id=i.id where ic.category_id=?1 and i.current_bid>=?2", nativeQuery = true)
    List<ItemEntity> findItemsByCategoryAndPriceAtLeast(Integer categoryId, Integer min);
    @Query(value = "select * from items i inner join item_category ic on ic.item_id=i.id where ic.category_id=?1 and i.current_bid<=?2", nativeQuery = true)
    List<ItemEntity> findItemsByCategoryAndPriceAtMost(Integer categoryId, Integer max);
    @Query(value = "select * from items i inner join item_category ic on ic.item_id=i.id where ic.category_id=?1 and i.current_bid>=?2 and i.current_bid<=?3", nativeQuery = true)
    List<ItemEntity> findItemsByCategoryAndPriceRange(Integer categoryId, Integer min, Integer max);

}
