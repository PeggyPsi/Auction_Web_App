package com.ted.auction_app.Services.Implementations;

import com.ted.auction_app.Models.Category.CategoryDTO;
import com.ted.auction_app.Models.Category.CategoryEntity;
import com.ted.auction_app.Models.Category.Enum.CategoryEnumDTO;
import com.ted.auction_app.Repositories.CategoryRepository;
import com.ted.auction_app.Services.Declarations.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        // Retrieve categories from database
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();

        // Create DTO list
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        // iterate through the list of retrieved users from the database
        categoryEntities.forEach((categoryEntity) -> {
            // create new DTO
            CategoryDTO categoryDTO = new CategoryDTO();

            // Copy entity in DTO
            BeanUtils.copyProperties(categoryEntity, categoryDTO);

            // Append DTO in return list
            categoryDTOs.add(categoryDTO);
        });

        return categoryDTOs;
    }

    @Override
    public void saveCategories(List<CategoryDTO> categories){
        // iterate through list of categories and save them in database
        for(CategoryDTO categoryDTO: categories){

            // Check whether entity already exists
            if(categoryRepository.findByName(categoryDTO.getName()) == null){
                //create entity
                CategoryEntity categoryEntity = new CategoryEntity();
                //copy DTO to entity
                BeanUtils.copyProperties(categoryDTO, categoryEntity);
                //save category
                categoryRepository.save(categoryEntity);
            }

        }
    }

    @Override
    public List<CategoryEnumDTO> getAllCategoriesEnum() {
        // Retrieve categories from database and their item counts
        // List<CategoryEnumDTO> categoryEnumDTOS = categoryRepository.categoryItemGroup();
        return categoryRepository.categoryItemGroup();
    }
}
