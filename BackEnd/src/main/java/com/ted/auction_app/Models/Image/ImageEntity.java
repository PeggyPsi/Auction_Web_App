package com.ted.auction_app.Models.Image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ted.auction_app.Models.Item.ItemEntity;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content", nullable = false)
    @Lob
    private byte[] content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnore
//    @OnDelete(action = OnDeleteAction.CASCADE)                          // when deleting an item corresponding images will also be deleted from database
    private ItemEntity item;

    public ImageEntity() {
    }

    public ImageEntity(String name, byte[] content) {
        this.name = name;
        this.content = content;
        this.item = new ItemEntity();
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
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
