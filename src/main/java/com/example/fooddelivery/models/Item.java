package com.example.fooddelivery.models;

public class Item {

    private Integer item_id;
    private String item_name;
    private String item_catagory;
    private Integer item_price;

    public Item() {
    }

    public Item(Integer item_id, String item_name, String item_catagory, Integer item_price) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_catagory = item_catagory;
        this.item_price = item_price;
    }

    public Integer getItem_id() {
        return this.item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return this.item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_catagory() {
        return this.item_catagory;
    }

    public void setItem_catagory(String item_catagory) {
        this.item_catagory = item_catagory;
    }

    public Integer getItem_price() {
        return this.item_price;
    }

    public void setItem_price(Integer item_price) {
        this.item_price = item_price;
    }
}
