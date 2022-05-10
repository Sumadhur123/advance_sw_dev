package com.example.fooddelivery.models;

public class Table {
    private Integer table_id;
    private String owner_name;
    private String no_of_people;
    private String restaurant;
    private String time;

    public Table() {
    }

    public Table(Integer table_id, String owner_name, String no_of_people,String restaurant, String time) {
        this.table_id = table_id;
        this.owner_name = owner_name;
        this.no_of_people = no_of_people;
        this.restaurant = restaurant;
        this.time = time;
    }

    public Integer getTable_id() {
        return table_id;
    }

    public void setTable_id(Integer table_id) {
        this.table_id = table_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getNo_of_people() {
        return no_of_people;
    }

    public void setNo_of_people(String no_of_people) {
        this.no_of_people = no_of_people;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

}
