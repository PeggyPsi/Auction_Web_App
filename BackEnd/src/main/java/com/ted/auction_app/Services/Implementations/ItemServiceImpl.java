package com.ted.auction_app.Services.Implementations;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.ted.auction_app.Models.Bid.BidDTO;
import com.ted.auction_app.Models.Bid.BidEntity;
import com.ted.auction_app.Models.Category.CategoryEntity;
import com.ted.auction_app.Models.Item.ItemDTO;
import com.ted.auction_app.Models.Item.ItemEntity;
import com.ted.auction_app.Models.Item.ItemXml;
import com.ted.auction_app.Models.Location.LocationEntity;
import com.ted.auction_app.Models.User.UserEntity;
import com.ted.auction_app.Repositories.*;
import com.ted.auction_app.Services.Declarations.BidService;
import com.ted.auction_app.Services.Declarations.ItemService;
import com.ted.auction_app.Utils.Utils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    BidService bidService;

    @Autowired
    BidRepository bidRepository;

    @Override
    public ItemDTO saveItem(ItemDTO itemDTO) {
        // Create item entity object
        ItemEntity itemEntity = itemDTO.copy2Entity(itemDTO);

        // User seller is already saved in database
        // retrieve him
        UserEntity sellerEntity = userRepository.findByUsername(itemEntity.getSeller().getUsername());

        itemEntity.setSeller(sellerEntity);
        itemEntity.setBuyer(null);
        itemEntity.setNoBids(0);
        itemEntity.setEnded(false);
        itemEntity.setActive(false);
        itemEntity.setCurrentBid(itemEntity.getFirstBid());

        Set<CategoryEntity> categories = new HashSet<>();
        for(CategoryEntity categoryEntity: itemEntity.getCategories()) {
            CategoryEntity category = categoryRepository.findByName(categoryEntity.getName());
            if(category == null) {
                category = new CategoryEntity();
                BeanUtils.copyProperties(categoryEntity, category);
                categoryRepository.save(category);
            }
            category.getItems().add(itemEntity);
            categories.add(category);
        }
        itemEntity.setCategories(categories);

        // Save location
        locationRepository.save(itemEntity.getLocation());

        // Save item
        ItemEntity savedItem = itemRepository.save(itemEntity);
        savedItem.setSeller(new UserEntity());
        savedItem.setCategories(new HashSet<>());
        savedItem.setLocation(new LocationEntity());

        itemDTO = itemEntity.copy2DTO(savedItem);

        return itemDTO;
    }

    @Transactional
    @Override
    public ItemDTO updateItem(ItemDTO itemDTO) {
        // Create item entity object
        ItemEntity itemEntity = itemDTO.copy2Entity(itemDTO);


        itemEntity.setBuyer(null);
        itemEntity.setNoBids(0);
        itemEntity.setCurrentBid(itemEntity.getFirstBid());
        itemEntity.setEnded(false);
        itemEntity.setActive(false);

        // CATEGORIES
//        categoryRepository.updateCategories(itemEntity);
        // UpdateItemCategories -> category service
        // Retrieve categories of item from database
        List<CategoryEntity> categoryEntities = categoryRepository.findByItemId(itemEntity.getId());
        // Iterate though database categories
        categoryEntities.forEach((category) -> {
            // If one category doesnt belong in new category list remove it from database
            if(itemEntity.getCategories().stream().noneMatch(ti -> ti.getName().equals(category.getName()))) {
                // Category has been deleted from updated item -> remove association from database
                System.out.println("INSIDE");
                category.getItems().remove(itemEntity);         ///////
                categoryRepository.deleteCategoryItemJoin(category.getId(), itemEntity.getId());
            }
        });

        // Execute below code -> return new list
        Set<CategoryEntity> categories = new HashSet<>();
        for(CategoryEntity categoryEntity: itemEntity.getCategories()) {
            CategoryEntity category = categoryRepository.findByName(categoryEntity.getName());
            if(category == null) {
                category = new CategoryEntity();
                BeanUtils.copyProperties(categoryEntity, category);
                categoryRepository.save(category);
            }
            if(category.getItems().stream().noneMatch(ti -> ti.getId().equals(itemEntity.getId()))) {
                category.getItems().remove(itemRepository.findItemEntityById(itemEntity.getId()));
            }
            category.getItems().add(itemEntity);
            categories.add(category);
        }
        itemEntity.setCategories(categories);

//        System.out.println(itemEntity.getCategories().isEmpty() || itemEntity.getSeller()==null || itemEntity.getLocation()==null);

        // LOCATION
        locationRepository.save(itemEntity.getLocation());
//        itemEntity.getLocation().setCountry();
//
//        // Save item
        ItemEntity savedItem = itemRepository.save(itemEntity);
//        savedItem.setSeller(new UserEntity());
//        savedItem.setCategories(new HashSet<>());
//        savedItem.setLocation(new LocationEntity());

        itemDTO = itemEntity.copy2DTO(savedItem);

        return itemDTO;
    }

    @Override
    @Transactional
    public void deleteItem(Integer itemId){
        // Before deleting item delete its pictures
        imageRepository.removeImageEntitiesByItem_Id(itemId);
        // Then delete item
        itemRepository.removeItemEntityById(itemId);
    }

    @Override
    public String extractToXml(Integer itemId) {
        // Retrieve Item from database
        ItemEntity itemEntity = itemRepository.findItemEntityById(itemId);

        // Convert it to xml entity for further processing
        ItemXml itemXml = itemEntity.copy2Xml(itemEntity);
//        itemXml.toString();

        // Start marshalling with JAXB
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ItemXml.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

            File newXmlFile = new File("XMLS/item" + itemXml.getId() + ".xml");
            if(newXmlFile.exists() && newXmlFile.isFile()) return "exists";

//            //Print XML String to Console
            jaxbMarshaller.marshal(itemXml, newXmlFile);
//            jaxbMarshaller.marshal(itemXml, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public String extractToJson(Integer itemId) {
        // Retrieve Item from database
        ItemEntity itemEntity = itemRepository.findItemEntityById(itemId);
        ItemXml itemXml = itemEntity.copy2Xml(itemEntity);

        ObjectMapper mapper = new ObjectMapper();

        try {
            File newJsonFile = new File("JSONS/item" + itemXml.getId() + ".json");
            if(newJsonFile.exists() && newJsonFile.isFile()) return "exists";

            //Convert object to JSON string and save into file directly
            mapper.writeValue(newJsonFile, itemXml);

//            //Convert object to JSON string
//            String jsonInString = mapper.writeValueAsString(itemXml);
////
//            //Convert object to JSON string and pretty print
//            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(itemXml);


        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public Integer startAuction(Integer itemId) {
        // Retrieve item from database
        ItemEntity itemEntity = itemRepository.findItemEntityById(itemId);

        // Check if item found
        if(itemEntity == null) return 2;

        // Check if already started
        if(itemEntity.getActive()) return 3;

        // Update and save
        itemEntity.setActive(true);
        itemRepository.save(itemEntity);

        return 1;
    }

    @Override
    public List<ItemDTO> searchByText(String text){
        List<ItemEntity> items = itemRepository.findByFreeText(text);
        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }



    ////////////////////////////// GET ITEMS
    public ItemDTO updateIfEnded(ItemDTO itemDTO) {
        // Check if ended based on current time and auction end time
        boolean notended = Utils.compareDates(itemDTO.getAuctionEnd());
        System.out.println("check if not ended "+ notended);

        // If ended
        if(!notended) {
            // set ended to true
            itemDTO.setEnded(true);

            // Retrieve top bid from database for specific item
            List<BidDTO> bids = bidService.getBids(itemDTO.getId(), 1);

            // Check if there are any bids
            if(!bids.isEmpty()) {
                // If there are retrieve top one
                BidDTO topBid = bids.get(0);

                // Update item with winner info
                itemDTO.setBuyer(topBid.getBidder());
                itemDTO.setBuyPrice(topBid.getAmount());

                // Update bid with win status "yes"
                topBid.setWinStatus("YES");

                // Convert bid DTO to entity
                BidEntity bidEntity = topBid.copy2Entity(topBid);

                // Save bid
                bidRepository.save(bidEntity);
            }

            // Convert item DTO to Entity
            ItemEntity itemEntity = itemDTO.copy2Entity(itemDTO);
            if(bids.isEmpty()) itemEntity.setBuyer(null);

            // Save item
            ItemEntity returnItem = itemRepository.save(itemEntity);
            // copy entity to DTO
            ItemDTO returnItemDTO = returnItem.copy2DTO(returnItem);
            return returnItemDTO;
        }


        return itemDTO;
    }
    public ItemDTO updateIfActive(ItemDTO itemDTO) {
        // Check if started based on current time and auction start time
        System.out.println("item time "+itemDTO.getAuctionStart());
        boolean notstarted = Utils.compareDates(itemDTO.getAuctionStart());
        System.out.println("check if not active "+ notstarted);

        // If auction has started
        if(!notstarted) {
            //set started true
            itemDTO.setActive(true);

            // Convert item DTO to Entity
            ItemEntity itemEntity = itemDTO.copy2Entity(itemDTO);
            itemEntity.setBuyer(null);

            // Save item
            ItemEntity returnItem = itemRepository.save(itemEntity);
            // copy entity to DTO
            ItemDTO returnItemDTO = returnItem.copy2DTO(returnItem);

            return returnItemDTO;
        }

        return itemDTO;
    }

    @Override
    public List<ItemDTO> getAllItems() {

        List<ItemEntity> items = itemRepository.findAllByOrderByName();
        List<ItemDTO> returnItems = new ArrayList<>();
        for(ItemEntity item: items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }
        return returnItems;
    }

    @Override
    public List<ItemDTO> getItemsByCategory(Integer categoryId) {
        List<ItemEntity> items = categoryRepository.getItemsByCategoryId(categoryId);
        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }

    @Override
    public  List<ItemDTO> getItemsByPrice(Integer min, Integer max) {
        List<ItemEntity> items;
        // Price at least
        if(max == 0) {
            items = itemRepository.findItemsByPriceAtLeast(min);
        }
        // Price at most
        else if (min == 0) {
            items = itemRepository.findItemsByPriceAtMost(max);
        }
        // Range
        else {
            items = itemRepository.findItemsByPriceRange(min, max);
        }

        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }

    @Override
    public List<ItemDTO> getItemsByCategoryAndPrice(Integer categoryId, Integer min, Integer max) {
        List<ItemEntity> items;
        // Price at least
        if(max == 0) {
            items = itemRepository.findItemsByCategoryAndPriceAtLeast(categoryId, min);
        }
        // Price at most
        else if (min == 0) {
            items = itemRepository.findItemsByCategoryAndPriceAtMost(categoryId, max);
        }
        // Range
        else {
            items = itemRepository.findItemsByCategoryAndPriceRange(categoryId, min, max);
        }

        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }

    @Override
    public ItemDTO getItem(Integer itemId) {
        // Retrieve item from database
        ItemEntity itemEntity = itemRepository.findItemEntityById(itemId);

        if (itemEntity == null) return null;

        // Copy item entity to DTO
        ItemDTO itemDTO = itemEntity.copy2DTO(itemEntity);

        // Check if item auction has started -> set active (only if not active)
        if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
        // Check if item must be updated with ended/bought (only if not ended)
        if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

        return itemDTO;
    }

    @Override
    public List<ItemDTO> getItemsNewest() {
        List<ItemEntity> items = itemRepository.findAllByOrderById();
        List<ItemDTO> returnItems = new ArrayList<>();
        for(ItemEntity item: items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }
        return returnItems;
    }


    //////////////////// SELLER
    @Override
    public List<ItemDTO> getItemsBySeller(String sellerId) {
        List<ItemEntity> items = itemRepository.findAllBySeller_PublicIdOrderByName(sellerId);
        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }
        return returnItems;
    }

    @Override
    public List<ItemDTO> getItemsBySellerAndCategory(String sellerId, Integer categoryId) {
        List<ItemEntity> items = itemRepository.findAllBySeller_PublicIdAndCategory_IdOrderByName(sellerId, categoryId);
        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }

    @Override
    public List<ItemDTO> getItemsBySellerAndPrice(String sellerId, Integer min, Integer max){
        List<ItemEntity> items;
        // Price at least
        if(max == 0) {
            items = itemRepository.findItemsBySellerAndPriceAtLeast(sellerId, min);
        }
        // Price at most
        else if (min == 0) {
            items = itemRepository.findItemsBySellerAndPriceAtMost(sellerId, max);
        }
        // Range
        else {
            items = itemRepository.findItemsBySellerAndPriceRange(sellerId, min, max);
        }

        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }

    @Override
    public List<ItemDTO> getItemsBySellerAndCategoryAndPrice(String sellerId, Integer categoryId, Integer min, Integer max) {
        List<ItemEntity> items;
        // Price at least
        if(max == 0) {
            items = itemRepository.findItemsBySellerAndCategoryAndPriceAtLeast(sellerId, categoryId, min);
        }
        // Price at most
        else if (min == 0) {
            items = itemRepository.findItemsBySellerAndCategoryAndPriceAtMost(sellerId, categoryId, max);
        }
        // Range
        else {
            items = itemRepository.findItemsBySellerAndCategoryAndPriceRange(sellerId, categoryId, min, max);
        }

        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }


    ////////////////// BIDDER

    // BID
    @Override
    public List<ItemDTO> getBidItemsByBidder(String bidderId) {
        List<ItemEntity> items = itemRepository.findAllItemsByBidder(bidderId);

        //Convert to DTO
        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }
    @Override
    public List<ItemDTO> getBidItemsByBidderAndCategory(String bidderId, Integer categoryId){
        List<ItemEntity> items = itemRepository.findAllItemsByBidderAndCategory(bidderId, categoryId);
        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }
    @Override
    public List<ItemDTO> getBidItemsByBidderAndPrice(String bidderId, Integer min, Integer max) {
        List<ItemEntity> items;
        // Price at least
        if(max == 0) {
            items = itemRepository.findAllItemsByBidderAndPriceAtLeast(bidderId, min);
        }
        // Price at most
        else if (min == 0) {
            items = itemRepository.findAllItemsByBidderAndPriceAtMost(bidderId, max);
        }
        // Range
        else {
            items = itemRepository.findAllItemsByBidderAndPriceRange(bidderId, min, max);
        }

        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }
    @Override
    public List<ItemDTO> getBidItemsByBidderAndCategoryAndPrice(String bidderId, Integer categoryId, Integer min, Integer max) {
        List<ItemEntity> items;
        // Price at least
        if(max == 0) {
            items = itemRepository.findAllItemsByBidderAndCategoryAndPriceAtLeast(bidderId, categoryId, min);
        }
        // Price at most
        else if (min == 0) {
            items = itemRepository.findAllItemsByBidderAndCategoryAndPriceAtMost(bidderId, categoryId, max);
        }
        // Range
        else {
            items = itemRepository.findAllItemsByBidderAndCategoryAndPriceRange(bidderId, categoryId, min, max);
        }

        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }

    // WON
    @Override
    public List<ItemDTO> getWonItemsByBidder(String bidderId) {
        List<ItemEntity> items = itemRepository.findWonItemsByBidder(bidderId);

        //Convert to DTO
        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }
    @Override
    public List<ItemDTO> getWonItemsByBidderAndCategory(String bidderId, Integer categoryId) {
        List<ItemEntity> items = itemRepository.findWonItemsByBidderAndCategory(bidderId, categoryId);

        //Convert to DTO
        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }
    @Override
    public List<ItemDTO> getWonItemsByBidderAndPrice(String bidderId, Integer min, Integer max) {
        List<ItemEntity> items;
        // Price at least
        if(max == 0) {
            items = itemRepository.findWonItemsByBidderAndPriceAtLeast(bidderId, min);
        }
        // Price at most
        else if (min == 0) {
            items = itemRepository.findWonItemsByBidderAndPriceAtMost(bidderId, max);
        }
        // Range
        else {
            items = itemRepository.findWonItemsByBidderAndPriceRange(bidderId, min, max);
        }

        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }
    @Override
    public List<ItemDTO> getWonItemsByBidderAndCategoryAndPrice(String bidderId, Integer categoryId, Integer min, Integer max) {
        List<ItemEntity> items;
        // Price at least
        if(max == 0) {
            items = itemRepository.findWonItemsByBidderAndCategoryAndPriceAtLeast(bidderId, categoryId, min);
        }
        // Price at most
        else if (min == 0) {
            items = itemRepository.findWonItemsByBidderAndCategoryAndPriceAtMost(bidderId, categoryId, max);
        }
        // Range
        else {
            items = itemRepository.findWonItemsByBidderAndCategoryAndPriceRange(bidderId, categoryId, min, max);
        }

        List<ItemDTO> returnItems = new ArrayList<>();
        for (ItemEntity item : items) {
            ItemDTO itemDTO = item.copy2DTO(item);

            // Check if item auction has started -> set active (only if not active)
            if(!itemDTO.getActive()) itemDTO = updateIfActive(itemDTO);
            // Check if item must be updated with ended/bought (only if not ended)
            if(!itemDTO.getEnded()) itemDTO = updateIfEnded(itemDTO);

            returnItems.add(itemDTO);
        }

        return returnItems;
    }


}
