package com.example.fooddelivery.repository;
import com.example.fooddelivery.models.Restaurant;
import com.example.fooddelivery.models.RestaurantMenu;
import com.example.fooddelivery.repository.actionInterface.RestaurantActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Component
public class RestaurantRepository implements RestaurantActions {

    private static final String getRestrauants = "SELECT * FROM Restaurant";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<RestaurantMenu> getRestaurant(){
        return jdbcTemplate.query(getRestrauants, new RowMapper<RestaurantMenu>() {
            @Override
            public RestaurantMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
                RestaurantMenu r = new RestaurantMenu();
                r.setRestaurantId(rs.getString("Restaurant_ID"));
                r.setRestaurantName(rs.getString("Restaurant_Name"));
                r.setRestaurantLocation(rs.getString("Restaurant_location"));
                return r;
            }
        });
    }

    public List<Restaurant> getRestaurantSales(Integer hotelId) {
        List<Restaurant> list = jdbcTemplate.query("SELECT re.Restaurant_ID, re.Restaurant_Name, re.Restaurant_location, SUM(Total_amount) as sale_amount\n" +
                "from Customer_Order co inner join Restaurant re on co.Restaurant_ID = re.Restaurant_ID\n" +
                "where re.Restaurant_ID != " + hotelId +
                " group by Restaurant_ID;", new RowMapper<Restaurant>() {
            @Override
            public Restaurant mapRow(ResultSet resultSet, int i) throws SQLException {
                Restaurant restaurant = new Restaurant();

                restaurant.Restaurant_ID = resultSet.getString("Restaurant_ID");
                restaurant.setRestaurantName(resultSet.getString("Restaurant_Name"));
                restaurant.setRestaurant_location(resultSet.getString("Restaurant_location"));
                restaurant.setSale_amount(resultSet.getInt("sale_amount"));
                return restaurant;
            }
        });
        return list;
    }
}
