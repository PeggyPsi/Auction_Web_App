package com.ted.auction_app.Models.Category;

import com.ted.auction_app.Models.Item.ItemDTO;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO{

    private Integer id;
    private String name;
    private List<ItemDTO> items;

    public CategoryDTO() {
        this.items = new ArrayList<>();
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        System.out.print("CategoryDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}');
        return null;
    }
}
