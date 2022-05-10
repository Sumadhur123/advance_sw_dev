package com.example.fooddelivery.models;

public class Cart {

    public int restaurantId;
    public String userName;
    public String itemName;
    public int itemPrice;
    public String restaurantName;
    public String uniqueId;
    public Cart(){

    }

    public Cart(int restaurantId,String userName,String itemName,int itemPrice,String restaurantName, String uniqueId){
        this.restaurantId=restaurantId;
        this.userName=userName;
        this.itemName=itemName;
        this.itemPrice=itemPrice;
        this.restaurantName=restaurantName;
        this.uniqueId=uniqueId;

    }

    public String getUserName(){return userName;}
    public String getRestaurant_name(){return restaurantName;}
    public void setUniqueId(String uniqueId){this.uniqueId =uniqueId;}
    public void setUserName(String userName){this.userName = userName;}
    public void setItemName(String itemName){this.itemName = itemName; }
    public void setItemPrice(int itemPrice) { this.itemPrice = itemPrice; }
    public void setRestaurant_name(String restaurantName) {this.restaurantName =restaurantName;}
    public void setRestaurantId(int restaurantId){this.restaurantId =restaurantId;}

}
