package com.ted.auction_app.Repositories;

import com.ted.auction_app.Models.Image.ImageEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ImageRepository extends CrudRepository<ImageEntity, Integer> {
    Set<ImageEntity> findByItem_Id(Integer itemId);
    void removeImageEntitiesByItem_Id(Integer itemId);
    void removeImageEntityById(Integer imageId);
}
