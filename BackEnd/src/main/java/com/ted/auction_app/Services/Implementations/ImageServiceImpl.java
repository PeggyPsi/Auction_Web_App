package com.ted.auction_app.Services.Implementations;

import com.ted.auction_app.Exceptions.FileStorageException;
import com.ted.auction_app.Models.Image.ImageDTO;
import com.ted.auction_app.Models.Image.ImageEntity;
import com.ted.auction_app.Models.Item.ItemEntity;
import com.ted.auction_app.Repositories.ImageRepository;
import com.ted.auction_app.Repositories.ItemRepository;
import com.ted.auction_app.Repositories.LocationRepository;
import com.ted.auction_app.Services.Declarations.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    LocationRepository locationRepository;

    @Override
    public ImageDTO uploadImage(MultipartFile image, Integer itemId) {
        // Retrieve item
        ItemEntity item = itemRepository.findItemEntityById(itemId);

        String fileName = StringUtils.cleanPath(image.getOriginalFilename());

        try {

            ImageEntity imageEntity = new ImageEntity(fileName, image.getBytes());
            imageEntity.setItem(item);

            ImageEntity savedImage = imageRepository.save(imageEntity);

            // Create DTO to return the image
            ImageDTO imageDTO = new ImageDTO(savedImage.getId(), savedImage.getName(), savedImage.getContent());

            return imageDTO;

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public Set<ImageDTO> downloadImages(Integer itemId) {
        // Initialize DTO set
        Set<ImageDTO> images = new HashSet<>();

        // Retrieve image entities from database
        Set<ImageEntity> imageEntities = imageRepository.findByItem_Id(itemId);

        if(!imageEntities.isEmpty()) {
            // Convert entities to DTOs
            for(ImageEntity imageEntity: imageEntities) {
                images.add(new ImageDTO(imageEntity.getId(), imageEntity.getName(), imageEntity.getContent()));
            }

            System.out.println("HEY");
        }

        return images;
    }

    @Override
    @Transactional
    public void deleteImage(Integer imageId){
        // Before deleting item delete its pictures
        imageRepository.removeImageEntityById(imageId);
    }
}
