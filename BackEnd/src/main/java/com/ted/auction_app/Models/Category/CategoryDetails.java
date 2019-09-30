package com.ted.auction_app.Models.Category;

import javax.validation.constraints.NotEmpty;

public class CategoryDetails {

    private Integer id;

    @NotEmpty(message = "{category.notEmpty}")
    private String name;

    public CategoryDetails() {
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
        System.out.println("CategoryDetails{" +
                "name='" + name + '\'' +
                "id='" + id + '\'' +
                '}');
        return null;
    }
}
