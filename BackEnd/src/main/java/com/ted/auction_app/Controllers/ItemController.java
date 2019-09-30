package com.ted.auction_app.Controllers;

import com.ted.auction_app.Errors.ErrorResponse;
import com.ted.auction_app.Errors.SuccessResponse;
import com.ted.auction_app.Models.Image.ImageDTO;
import com.ted.auction_app.Models.Image.ImageDetails;
import com.ted.auction_app.Models.Item.ItemDTO;
import com.ted.auction_app.Models.Item.ItemDetailsRequest;
import com.ted.auction_app.Models.Item.ItemDetailsResponse;
import com.ted.auction_app.Services.Declarations.CategoryService;
import com.ted.auction_app.Services.Declarations.ImageService;
import com.ted.auction_app.Services.Declarations.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ItemController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/items/getAll", method = RequestMethod.GET)
    public ResponseEntity<?> getAllItems(@RequestParam(value = "category", required = false) Integer categoryId,
                                         @RequestParam(value = "price", required = false) Boolean price,
                                         @RequestParam(value = "min", required = false) Integer min,
                                         @RequestParam(value = "max", required = false) Integer max) {

        List<ItemDTO> items = new ArrayList<>();

        // Check if must be filtered
        if(categoryId == null && price == null) {
            items = itemService.getAllItems();
        }
        // Check if only category filtered
        else if(categoryId != null && price == null) {
            items = itemService.getItemsByCategory(categoryId);
        }
        // Check if only Price
        else if(categoryId == null && price != null) {
            items = itemService.getItemsByPrice(min, max);
        }
        else if(categoryId != null && price != null) {
            items = itemService.getItemsByCategoryAndPrice(categoryId, min, max);
        }

        // Check if items were found
        if(items.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No items could be found. Maybe try later for new created auctions!", null), HttpStatus.NOT_FOUND);

        // Convert items to response details
        // Convert it to details
        List<ItemDetailsResponse> allItems = new ArrayList<>();
        for(ItemDTO item: items) {
            ItemDetailsResponse returnItem = item.copy2DetailsRes(item);
            // Retrieve images
            Set<ImageDTO> images = imageService.downloadImages(returnItem.getId());
            if (!images.isEmpty()) {
                // Convert them to details model
                Set<ImageDetails> imageDetails = new HashSet<>();
                for(ImageDTO imageDTO: images) {
                    imageDetails.add(new ImageDetails(imageDTO.getId(), imageDTO.getName(), imageDTO.getContent()));
                }

                // Attach final images to returned item
                returnItem.setImages(imageDetails);
            }
            allItems.add(returnItem);
        }

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/newest", method = RequestMethod.GET)
    public ResponseEntity<?> getItemsNewest() {

        // Retrieve all items
        List<ItemDTO> items = itemService.getItemsNewest();

        // Check if items were found
        if(items.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No items could be found. Maybe try later for new created auctions!", null), HttpStatus.NOT_FOUND);

        // Convert items to response details
        // Convert it to details
        List<ItemDetailsResponse> allItems = new ArrayList<>();
        for(ItemDTO item: items) {
            ItemDetailsResponse returnItem = item.copy2DetailsRes(item);
            // Retrieve images
            Set<ImageDTO> images = imageService.downloadImages(returnItem.getId());
            if (!images.isEmpty()) {
                // Convert them to details model
                Set<ImageDetails> imageDetails = new HashSet<>();
                for(ImageDTO imageDTO: images) {
                    imageDetails.add(new ImageDetails(imageDTO.getId(), imageDTO.getName(), imageDTO.getContent()));
                }

                // Attach final images to returned item
                returnItem.setImages(imageDetails);
            }
            allItems.add(returnItem);
        }

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDetailsRequest item) {

        // Item DTO
        ItemDTO itemDTO = item.copy2DTO(item);

        // Save item
        // Also return from save the id of the item
        ItemDTO savedItem = itemService.saveItem(itemDTO);

        return new ResponseEntity<>(savedItem, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/update", method = RequestMethod.PUT)
    public ResponseEntity<ItemDTO> updateItem(@RequestBody ItemDTO item) {

        item.toString();

        ItemDTO savedItem = itemService.updateItem(item);

        return new ResponseEntity<>(savedItem, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<?> getItem(@PathVariable("itemId") Integer itemId) {

        // Item from database as DTO
        ItemDTO itemDTO = itemService.getItem(itemId);

        // No file was found
        if(itemDTO == null) {
            return new ResponseEntity<>(new ErrorResponse("Item was not found", null), HttpStatus.NOT_FOUND);
        }



        // Convert it to details
        ItemDetailsResponse returnItem = itemDTO.copy2DetailsRes(itemDTO);

        // Retrieve images
        Set<ImageDTO> images = imageService.downloadImages(returnItem.getId());
        if (!images.isEmpty()) {
            // Convert them to details model
            Set<ImageDetails> imageDetails = new HashSet<>();
            for(ImageDTO imageDTO: images) {
                imageDetails.add(new ImageDetails(imageDTO.getId(), imageDTO.getName(), imageDTO.getContent()));
            }

            // Attach final images to returned item
            returnItem.setImages(imageDetails);
        }

        return new ResponseEntity<>(returnItem, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") Integer itemId) {
        itemService.deleteItem(itemId);
        return new ResponseEntity<>(new SuccessResponse("Item was deleted successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/items/seller/{sellerId}", method = RequestMethod.GET)
    public ResponseEntity<?> getItemsBySeller(@PathVariable("sellerId") String sellerId,
                                              @RequestParam(value = "category", required = false) Integer categoryId,
                                              @RequestParam(value = "price", required = false) Boolean price,
                                              @RequestParam(value = "min", required = false) Integer min,
                                              @RequestParam(value = "max", required = false) Integer max) {

        List<ItemDTO> items = new ArrayList<>();

        // Check if must be filtered
        if(categoryId == null && price == null) {
            items = itemService.getItemsBySeller(sellerId);
        }
        // Check if only category filtered
        else if(categoryId != null && price == null) {
            items = itemService.getItemsBySellerAndCategory(sellerId, categoryId);
        }
        // Check if only Price
        else if(categoryId == null && price != null) {
            items = itemService.getItemsBySellerAndPrice(sellerId, min, max);
        }
        else if(categoryId != null && price != null) {
            items = itemService.getItemsBySellerAndCategoryAndPrice(sellerId, categoryId, min, max);
        }

        // Check if items were found
        if(items.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No items could be found from specific seller.", null), HttpStatus.NOT_FOUND);

        // Convert items to response details
        // Convert it to details
        List<ItemDetailsResponse> allItems = new ArrayList<>();
        for(ItemDTO item: items) {
            ItemDetailsResponse returnItem = item.copy2DetailsRes(item);
            // Retrieve images
            Set<ImageDTO> images = imageService.downloadImages(returnItem.getId());
            if (!images.isEmpty()) {
                // Convert them to details model
                Set<ImageDetails> imageDetails = new HashSet<>();
                for(ImageDTO imageDTO: images) {
                    imageDetails.add(new ImageDetails(imageDTO.getId(), imageDTO.getName(), imageDTO.getContent()));
                }

                // Attach final images to returned item
                returnItem.setImages(imageDetails);
            }
            allItems.add(returnItem);
        }

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/toXML/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<?> extractToXML(@PathVariable("itemId") Integer itemId) {
        //check whether file already exists
        if(itemService.extractToXml(itemId).equals("exists")) return new ResponseEntity<>(new ErrorResponse("The requested item has already been extracted to XML form", null), HttpStatus.CONFLICT );

        return new ResponseEntity<>(new SuccessResponse("Item extracted successfully."), HttpStatus.OK);
    }

    @RequestMapping(value = "/items/toJSON/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<?> extractToJSON(@PathVariable("itemId") Integer itemId) {
        if(itemService.extractToJson(itemId).equals("exists")) return new ResponseEntity<>(new ErrorResponse("The requested item has already been extracted to JSON form", null), HttpStatus.CONFLICT );

        return new ResponseEntity<>(new SuccessResponse("Item extracted successfully."), HttpStatus.OK);
    }

    @RequestMapping(value = "/items/category/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<?> getItemsByCategory(@PathVariable("categoryId") Integer categoryId){

        // TODO complete implementation of this request
        List<ItemDTO> items = itemService.getItemsByCategory(categoryId);

        // Check if items were found
        if(items.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No items could be found for specific category.", null), HttpStatus.NOT_FOUND);

        // Convert items to response details
        // Convert it to details
        List<ItemDetailsResponse> allItems = new ArrayList<>();
        for(ItemDTO item: items) {
            ItemDetailsResponse returnItem = item.copy2DetailsRes(item);
            // Retrieve images
            Set<ImageDTO> images = imageService.downloadImages(returnItem.getId());
            if (!images.isEmpty()) {
                // Convert them to details model
                Set<ImageDetails> imageDetails = new HashSet<>();
                for(ImageDTO imageDTO: images) {
                    imageDetails.add(new ImageDetails(imageDTO.getId(), imageDTO.getName(), imageDTO.getContent()));
                }

                // Attach final images to returned item
                returnItem.setImages(imageDetails);
            }
            allItems.add(returnItem);
        }

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/{itemId}/start", method = RequestMethod.GET)
    public ResponseEntity<?> startAuction(@PathVariable("itemId") Integer itemId){

        Integer ok = itemService.startAuction(itemId);

        if(ok == 1) return new ResponseEntity<>(new SuccessResponse("Auction started successfully"), HttpStatus.OK);
        else if(ok == 2) return new ResponseEntity<>(new ErrorResponse("Item could not be found", null), HttpStatus.NOT_ACCEPTABLE);
        else return new ResponseEntity<>(new ErrorResponse("Auction has already started", null), HttpStatus.NOT_ACCEPTABLE);

    }

    @RequestMapping(value = "/items/bids/by/{bidderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getBidItems(@PathVariable("bidderId") String bidderId,
                                         @RequestParam(value = "category", required = false) Integer categoryId,
                                         @RequestParam(value = "price", required = false) Boolean price,
                                         @RequestParam(value = "min", required = false) Integer min,
                                         @RequestParam(value = "max", required = false) Integer max) {

        List<ItemDTO> items = new ArrayList<>();

        // Check if must be filtered
        if(categoryId == null && price == null) {
            items = itemService.getBidItemsByBidder(bidderId);
        }
        // Check if only category filtered
        else if(categoryId != null && price == null) {
            items = itemService.getBidItemsByBidderAndCategory(bidderId, categoryId);
        }
        // Check if only Price
        else if(categoryId == null && price != null) {
            items = itemService.getBidItemsByBidderAndPrice(bidderId, min, max);
        }
        else if(categoryId != null && price != null) {
            items = itemService.getBidItemsByBidderAndCategoryAndPrice(bidderId, categoryId, min, max);
        }

        // Check if items were found
        if(items.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No items found.", null), HttpStatus.NOT_FOUND);

        // Convert items to response details
        // Convert it to details
        List<ItemDetailsResponse> allItems = new ArrayList<>();
        for(ItemDTO item: items) {
            ItemDetailsResponse returnItem = item.copy2DetailsRes(item);
            // Retrieve images
            Set<ImageDTO> images = imageService.downloadImages(returnItem.getId());
            if (!images.isEmpty()) {
                // Convert them to details model
                Set<ImageDetails> imageDetails = new HashSet<>();
                for(ImageDTO imageDTO: images) {
                    imageDetails.add(new ImageDetails(imageDTO.getId(), imageDTO.getName(), imageDTO.getContent()));
                }

                // Attach final images to returned item
                returnItem.setImages(imageDetails);
            }
            allItems.add(returnItem);
        }

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/bids/won/by/{bidderId}", method = RequestMethod.GET)
    public ResponseEntity<?> getWonItems(@PathVariable("bidderId") String bidderId,
                                         @RequestParam(value = "category", required = false) Integer categoryId,
                                         @RequestParam(value = "price", required = false) Boolean price,
                                         @RequestParam(value = "min", required = false) Integer min,
                                         @RequestParam(value = "max", required = false) Integer max) {

        List<ItemDTO> items = new ArrayList<>();

        // Check if must be filtered
        if(categoryId == null && price == null) {
            items = itemService.getWonItemsByBidder(bidderId);
        }
        // Check if only category filtered
        else if(categoryId != null && price == null) {
            items = itemService.getWonItemsByBidderAndCategory(bidderId, categoryId);
        }
        // Check if only Price
        else if(categoryId == null && price != null) {
            items = itemService.getWonItemsByBidderAndPrice(bidderId, min, max);
        }
        else if(categoryId != null && price != null) {
            items = itemService.getWonItemsByBidderAndCategoryAndPrice(bidderId, categoryId, min, max);
        }

        // Check if items were found
        if(items.isEmpty()) return new ResponseEntity<>(new ErrorResponse("You have not won any items yet", null), HttpStatus.NOT_FOUND);

        // Convert items to response details
        // Convert it to details
        List<ItemDetailsResponse> allItems = new ArrayList<>();
        for(ItemDTO item: items) {
            ItemDetailsResponse returnItem = item.copy2DetailsRes(item);
            // Retrieve images
            Set<ImageDTO> images = imageService.downloadImages(returnItem.getId());
            if (!images.isEmpty()) {
                // Convert them to details model
                Set<ImageDetails> imageDetails = new HashSet<>();
                for(ImageDTO imageDTO: images) {
                    imageDetails.add(new ImageDetails(imageDTO.getId(), imageDTO.getName(), imageDTO.getContent()));
                }

                // Attach final images to returned item
                returnItem.setImages(imageDetails);
            }
            allItems.add(returnItem);
        }

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/search", method = RequestMethod.GET)
    public ResponseEntity<?> freeTextSearch(@RequestParam("text") String text) {

        List<ItemDTO> items = itemService.searchByText(text);

        // Check if items were found
        if(items.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No items could be found.", null), HttpStatus.NOT_FOUND);

        // Convert items to response details
        // Convert it to details
        List<ItemDetailsResponse> allItems = new ArrayList<>();
        for(ItemDTO item: items) {
            ItemDetailsResponse returnItem = item.copy2DetailsRes(item);
            // Retrieve images
            Set<ImageDTO> images = imageService.downloadImages(returnItem.getId());
            if (!images.isEmpty()) {
                // Convert them to details model
                Set<ImageDetails> imageDetails = new HashSet<>();
                for(ImageDTO imageDTO: images) {
                    imageDetails.add(new ImageDetails(imageDTO.getId(), imageDTO.getName(), imageDTO.getContent()));
                }

                // Attach final images to returned item
                returnItem.setImages(imageDetails);
            }
            allItems.add(returnItem);
        }

        return new ResponseEntity<>(allItems, HttpStatus.OK);
    }

}
