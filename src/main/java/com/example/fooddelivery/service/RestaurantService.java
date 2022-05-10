package com.example.fooddelivery.service;

import java.util.List;

import com.example.fooddelivery.models.Restaurant;
import com.example.fooddelivery.models.RestaurantMenu;
import com.example.fooddelivery.repository.actionInterface.RestaurantActions;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private RestaurantActions restaurantActions;

    public RestaurantService(RestaurantActions restaurantActions) {
        this.restaurantActions = restaurantActions;
    }

    public List<RestaurantMenu> getRestaurants(){
        List<RestaurantMenu> restaurants = restaurantActions.getRestaurant();
        return restaurants;
    }

    public List<Restaurant> getRestaurantSales(Integer hotelId) {
        List<Restaurant> list = restaurantActions.getRestaurantSales(hotelId);
        return list;
    }

}
