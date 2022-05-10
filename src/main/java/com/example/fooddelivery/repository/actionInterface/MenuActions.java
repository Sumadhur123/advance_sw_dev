package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.RestaurantMenu;

import java.util.List;

public interface MenuActions {
    public List<RestaurantMenu> getMenu(String rest);
}
