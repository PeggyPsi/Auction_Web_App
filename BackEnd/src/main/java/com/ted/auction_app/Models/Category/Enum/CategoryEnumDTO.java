package com.ted.auction_app.Models.Category.Enum;

public class CategoryEnumDTO {
    private Integer id;
    private String name;
    private Long itemsCount;

    public CategoryEnumDTO() {
    }

    public CategoryEnumDTO(Integer id, String name, Long itemsCount) {
        this.id = id;
        this.name = name;
        this.itemsCount = itemsCount;
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

    public Long getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Long itemsCount) {
        this.itemsCount = itemsCount;
    }

    @Override
    public String toString() {
        System.out.print("CategoryDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", itemsCount='" + itemsCount + '\'' +
                '}');
        return null;
    }
}
