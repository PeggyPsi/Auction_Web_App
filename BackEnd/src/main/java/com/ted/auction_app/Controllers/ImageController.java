package com.ted.auction_app.Controllers;

import com.ted.auction_app.Errors.SuccessResponse;
import com.ted.auction_app.Models.Image.ImageDTO;
import com.ted.auction_app.Services.Declarations.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/items/{itemId}/images", method = RequestMethod.POST)
    public ResponseEntity<?> createItem(@RequestParam("image") MultipartFile image,
                                     @PathVariable("itemId") Integer itemId) {

        ImageDTO imageDTO = imageService.uploadImage(image, itemId);

        return new ResponseEntity<>(imageDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/images/{imageId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImage(@PathVariable("imageId") Integer imageId) {

        imageService.deleteImage(imageId);
        return new ResponseEntity<>(new SuccessResponse("Image was deleted successfully"), HttpStatus.OK);
    }

}
