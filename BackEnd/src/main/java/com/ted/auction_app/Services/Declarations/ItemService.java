package com.ted.auction_app.Services.Declarations;

import com.ted.auction_app.Models.Item.ItemDTO;

import java.util.List;

public interface ItemService {
    ItemDTO saveItem(ItemDTO itemDTO);
    ItemDTO updateItem(ItemDTO itemDTO);
    ItemDTO getItem(Integer itemId);
    List<ItemDTO> searchByText(String text);
    void deleteItem(Integer itemId);
    List<ItemDTO> getAllItems();
    List<ItemDTO> getItemsByCategory(Integer categoryId);
    String extractToXml(Integer itemId);
    String extractToJson(Integer itemId);
    List<ItemDTO> getItemsNewest();
    List<ItemDTO> getItemsByPrice(Integer min, Integer max);
    List<ItemDTO> getItemsByCategoryAndPrice(Integer categoryId, Integer min, Integer max);

    Integer startAuction(Integer itemId);

    // SELLER
    List<ItemDTO> getItemsBySeller(String sellerId);
    List<ItemDTO> getItemsBySellerAndCategory(String sellerId, Integer categoryId);
    List<ItemDTO> getItemsBySellerAndPrice(String sellerId, Integer min, Integer max);
    List<ItemDTO> getItemsBySellerAndCategoryAndPrice(String sellerId, Integer categoryId, Integer min, Integer max);

    // BIDDER
    List<ItemDTO> getBidItemsByBidder(String bidderId);
    List<ItemDTO> getBidItemsByBidderAndCategory(String bidderId, Integer categoryId);
    List<ItemDTO> getBidItemsByBidderAndPrice(String bidderId, Integer min, Integer max);
    List<ItemDTO> getBidItemsByBidderAndCategoryAndPrice(String bidderId, Integer categoryId, Integer min, Integer max);

    List<ItemDTO> getWonItemsByBidder(String bidderId);
    List<ItemDTO> getWonItemsByBidderAndCategory(String bidderId, Integer categoryId);
    List<ItemDTO> getWonItemsByBidderAndPrice(String bidderId, Integer min, Integer max);
    List<ItemDTO> getWonItemsByBidderAndCategoryAndPrice(String bidderId, Integer categoryId, Integer min, Integer max);
}
