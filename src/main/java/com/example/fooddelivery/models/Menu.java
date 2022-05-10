package com.example.fooddelivery.models;

public class Menu {

  public int restaurantID;
  public String uniqueId;
  public String itemName;
  public String restaurantName;
  public int itemPrice;
  public String userName;

  public  Menu(){

  }


  public Menu(int restaurantID, String userName, String itemName, int itemPrice, String restaurantName, String uniqueId) {
    this.restaurantID=restaurantID;
    this.userName=userName;
    this.itemName=itemName;
    this.itemPrice=itemPrice;
    this.restaurantName=restaurantName;
    this.uniqueId=uniqueId;
  }

  public String getRestaurant_name(String restaurantName) {
    return restaurantName;
  }

  public void setRestaurant_name(String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public void setItemPrice(int itemPrice) {
    this.itemPrice = itemPrice;
  }

  public void setRestaurantID(int restaurantID) {
    this.restaurantID = restaurantID;
  }

  public String getItemName(String itemName) {
    return itemName;
  }

  public String getUniqueId(String uniqueId) {
    return uniqueId;
  }

  public String getUserName(String userName) {
    return userName;
  }

  public int getRestaurantId(int restaurantID) {
    return restaurantID;
  }

  public int getItemPrice(int itemPrice) {
    return itemPrice;
  }
}
