package com.example.fooddelivery.models;

public class RestaurantMenu {

    public String restaurantId;
    public String restaurantName;
    public String restaurantLocation;
    public String itemName;
    public int itemPrice;
    public String uniqueId;


    public RestaurantMenu(){

    }
    public RestaurantMenu(String restaurantName, String itemName, int itemPrice){
        this.restaurantName=restaurantName;
        this.itemName=itemName;
        this.itemPrice=itemPrice;
    }


    public int getRestaurant_ID(int restaurantId){return restaurantId;}

    public void setRestaurantName(String restaurantName){
        this.restaurantName = restaurantName;
    }

    public void setRestaurantLocation(String restaurantLocation){
        this.restaurantLocation = restaurantLocation;
    }

    public void setItemName(String itemName){this.itemName = itemName; }

    public void setItemPrice(int itemPrice) { this.itemPrice = itemPrice; }

    public void setUniqueId(String uniqueId){this.uniqueId=uniqueId;}

    public void setRestaurantId(String restaurantId){this.restaurantId = restaurantId;}

}
