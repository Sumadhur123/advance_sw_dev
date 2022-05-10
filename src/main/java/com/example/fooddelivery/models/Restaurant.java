package com.example.fooddelivery.models;

public class Restaurant {

    public String Restaurant_ID;
    public String Restaurant_Name;
    public String Restaurant_location;
    public String Item_name;
    public int Item_price;
    public String Unique_id;
    public Integer sale_amount;

    public Integer getSale_amount() {
        return sale_amount;
    }

    public void setSale_amount(Integer sale_amount) {
        this.sale_amount = sale_amount;
    }

    public String getRestaurantName(){
        return  Restaurant_Name;
    }

    public String getRestaurant_location(){
        return Restaurant_location;
    }

    public String getItem_name(){return Item_name; }

    public int getItem_price() { return Item_price; }

    public String getUnique_id(){ return Unique_id;}

    public String getRestaurant_ID(){return Restaurant_ID;}

    public void setRestaurantName(String Restaurant_Name){
        this.Restaurant_Name = Restaurant_Name;
    }

    public void setRestaurant_location(String Restaurant_location){
        this.Restaurant_location= Restaurant_location;
    }

    public void setItem_name(String Item_name){this.Item_name = Item_name; }

    public void setItem_price(int Item_price) { this.Item_price = Item_price; }

    public void setUnique_id(String Unique_id){this.Unique_id=Unique_id;}

    public void setRestaurant_ID(String R_ID){this.Restaurant_ID=Restaurant_ID;}

}
