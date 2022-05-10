package com.example.fooddelivery.repository;

import com.example.fooddelivery.models.RestaurantMenu;
import com.example.fooddelivery.repository.actionInterface.SearchActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Component
public class SearchResultRepository implements SearchActions {

    private static final String search =  "SELECT Restaurant.Restaurant_Name, Restaurant.Restaurant_location,Menu.Item_name,Menu.Item_price,Menu.Unique_id FROM Restaurant INNER JOIN Menu  ON Restaurant.Restaurant_ID = Menu.Restaurant_ID";
    private static final String condition1 =" WHERE Restaurant.Restaurant_name =";
    private static final String condition2 = " OR Menu.Item_Name=";
    private static final String condition3 = " OR Menu.Item_price<";
    private static final String condition4 = " OR Restaurant.Restaurant_location=";


    @Autowired
    private JdbcTemplate jdbcTemplate;

   public List<RestaurantMenu> getData(String keyword){
        return jdbcTemplate.query(search+condition1+"'"+keyword+"'"+condition2+"'"+keyword+"'"+condition3+"'"+keyword+"'"+condition4+"'"+keyword+"';", new RowMapper<RestaurantMenu>() {
            @Override
            public RestaurantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
                RestaurantMenu r = new RestaurantMenu();
                r.setRestaurantName(rs.getString("Restaurant_Name"));
                r.setRestaurantLocation(rs.getString("Restaurant_location"));
                r.setItemName(rs.getString("Item_name"));
                r.setItemPrice(rs.getInt("Item_price"));
                r.setUniqueId(rs.getString("Unique_id"));
                return r;
            }
        });
    }
}