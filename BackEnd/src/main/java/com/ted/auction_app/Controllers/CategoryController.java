package com.ted.auction_app.Controllers;

import com.ted.auction_app.Errors.ErrorResponse;
import com.ted.auction_app.Models.Category.CategoryDTO;
import com.ted.auction_app.Models.Category.Enum.CategoryEnumDTO;
import com.ted.auction_app.Services.Declarations.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity<?> getCategories(){
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();

        if(categoryDTOs.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No categories found", null), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }


    // TODO not used DONT USE
    @RequestMapping(value = "/categories/enum", method = RequestMethod.GET)
    public ResponseEntity<?> getCategoriesEnum(){
        List<CategoryEnumDTO> categoryEnumDTOs = categoryService.getAllCategoriesEnum();

        if(categoryEnumDTOs.isEmpty()) return new ResponseEntity<>(new ErrorResponse("No categories found", null), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(categoryEnumDTOs, HttpStatus.OK);
    }
}
