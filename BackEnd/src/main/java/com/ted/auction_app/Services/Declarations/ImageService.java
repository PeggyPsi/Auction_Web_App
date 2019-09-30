package com.ted.auction_app.Services.Declarations;

import com.ted.auction_app.Models.Image.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface ImageService {
    ImageDTO uploadImage(MultipartFile image, Integer itemId);
    Set<ImageDTO> downloadImages(Integer itemId);
    void deleteImage(Integer imageId);
}
