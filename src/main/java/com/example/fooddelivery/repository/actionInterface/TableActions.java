package com.example.fooddelivery.repository.actionInterface;

import com.example.fooddelivery.models.Item;
import com.example.fooddelivery.models.Table;

import java.util.List;

public interface TableActions {
	
    List<Table> listAll();
    void save(Table table);
    void delete(Integer id);
    void update(Table table);
    Table get(Integer id);
}
