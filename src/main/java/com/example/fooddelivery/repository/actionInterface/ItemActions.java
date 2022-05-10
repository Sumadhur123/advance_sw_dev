package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.Item;
import java.util.List;

public interface ItemActions {
	
    List<Item> listAll();
    void save(Item item);
    void delete(Integer id);
    void update(Item item);
    Item get(Integer id);
}
