package com.ted.auction_app.Models.Image;

import com.ted.auction_app.Models.Item.ItemDTO;

public class ImageDTO {

    private Integer id;
    private String name;
    private byte[] content;
    private ItemDTO item;

    public ImageDTO() {
    }

    public ImageDTO(Integer id, String name, byte[] content) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.item = new ItemDTO();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }
}
