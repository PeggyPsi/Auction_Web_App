package com.ted.auction_app.Services.Declarations;

import com.ted.auction_app.Models.Category.CategoryDTO;
import com.ted.auction_app.Models.Category.Enum.CategoryEnumDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    void saveCategories(List<CategoryDTO> categories);
    List<CategoryEnumDTO> getAllCategoriesEnum();
}
