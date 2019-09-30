package com.ted.auction_app.Models.Category;

import javax.xml.bind.annotation.XmlValue;

public class CategoryXml {

    private String name;

    @XmlValue
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryXml() {
    }

    @Override
    public String toString() {
        System.out.println("CategoryXml{" +
                "name='" + name + '\'' +
                '}');
        return null;
    }
}
