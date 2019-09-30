package com.ted.auction_app.Models.Image;

public class ImageDetails {

    private Integer id;
    private String name;
    private byte[] content;

    public ImageDetails() {
    }

    public ImageDetails(Integer id, String name, byte[] content) {
        this.id = id;
        this.name = name;
        this.content = content;
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
}
