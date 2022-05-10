package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.Restaurant;
import com.example.fooddelivery.models.RestaurantMenu;

import java.util.List;

public interface RestaurantActions {

    public List<RestaurantMenu> getRestaurant();
    public List<Restaurant> getRestaurantSales(Integer hotelId);
}
