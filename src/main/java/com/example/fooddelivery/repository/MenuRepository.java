package com.example.fooddelivery.repository;
import com.example.fooddelivery.models.RestaurantMenu;
import com.example.fooddelivery.repository.actionInterface.MenuActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class MenuRepository implements MenuActions {
    private static final String getMenuItems = "SELECT * FROM Menu WHERE Restaurant_Name=";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RestaurantMenu> getMenu(String rest){
        return jdbcTemplate.query(getMenuItems + "'"+rest+"';", new RowMapper<RestaurantMenu>() {
            @Override
            public RestaurantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
                RestaurantMenu r = new RestaurantMenu();
                r.getRestaurant_ID(rs.getInt("Restaurant_ID"));
                r.setRestaurantName(rs.getString("Restaurant_Name"));
                r.setItemName(rs.getString("Item_name"));
                r.setItemPrice(rs.getInt("Item_price"));
                r.setUniqueId(rs.getString("Unique_id"));
                return r;
            }
        });
    }
}
