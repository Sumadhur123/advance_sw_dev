package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.Cart;
import com.example.fooddelivery.models.Menu;

import java.util.List;

public interface CartActions {

    public List<Menu> getItem(String Unique_id);
    public String addCart(String Unique_id,String User_name,int Restaurant_ID,String Restaurant_name,String Item_name,int Item_price);
    public List<Cart> getFromCart(String User_name);
    public List<String> getOrderIdsFromItems(List<Cart> cartItems);

    List<Menu> getFrequentItems(List<Cart> cartItems, List<String> orderIDS);
}
