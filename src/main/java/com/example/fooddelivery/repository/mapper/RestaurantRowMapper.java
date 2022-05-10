package com.example.fooddelivery.repository.mapper;

import com.example.fooddelivery.models.RestaurantMenu;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantRowMapper implements RowMapper<RestaurantMenu> {
  @Override
  public RestaurantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
      RestaurantMenu r = new RestaurantMenu();
      r.setRestaurantName(rs.getString("Restaurant_Name"));
      r.setRestaurantLocation(rs.getString("Restaurant_location"));
      r.setItemName(rs.getString("Item_name"));
      r.setItemPrice(rs.getInt("Item_price"));
      return r;
  }
}