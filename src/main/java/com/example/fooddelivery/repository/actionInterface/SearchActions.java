package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.RestaurantMenu;

import java.util.List;

public interface SearchActions {
    
	public List<RestaurantMenu> getData(String keyword);
}
