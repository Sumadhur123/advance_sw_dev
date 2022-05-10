package com.example.fooddelivery.repository;
import com.example.fooddelivery.models.Cart;
import com.example.fooddelivery.models.Menu;
import com.example.fooddelivery.repository.actionInterface.CartActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class CartRepository implements CartActions {

    private static final String addCart = "INSERT INTO Cart(Unique_id,User_name,Restaurant_ID,Restaurant_name,Item_name,Item_price) VALUES (?,?,?,?,?,?)";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Menu> getItem(String uniqueId){
        return jdbcTemplate.query("SELECT Item_name,Item_price,Restaurant_Name,Restaurant_ID FROM Menu where Unique_id=" + "'" + uniqueId + "';", new RowMapper<Menu>() {
            @Override
            public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {
                Menu m = new Menu();
                m.setRestaurantID(rs.getInt("Restaurant_ID"));
                m.setRestaurant_name(rs.getString("Restaurant_Name"));
                m.setItemName(rs.getString("Item_name"));
                m.setItemPrice(rs.getInt("Item_price"));

                return m;
            }
        });
    }

    public String addCart(String uniqueId,String userName,int restaurantID,String restaurantName,String itemName, int itemPrice){
      jdbcTemplate.update(addCart,uniqueId,userName,restaurantID,restaurantName,itemName,itemPrice);
      return null;
    }

    public List<Cart> getFromCart(String userName){
        return jdbcTemplate.query("SELECT * FROM Cart WHERE User_name="+"'"+userName+"';", new RowMapper<Cart>() {
            @Override
            public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
                Cart c = new Cart();

                c.setUserName(rs.getString("User_name"));
                c.setRestaurantId(rs.getInt("Restaurant_ID"));
                c.setRestaurant_name(rs.getString("Restaurant_Name"));
                c.setUniqueId(rs.getString("Unique_id"));
                c.setItemName(rs.getString("Item_name"));
                c.setItemPrice(rs.getInt("Item_price"));


                return c;
            }
        });
    }

    public List<String> getOrderIdsFromItems(List<Cart> cartItems) {
        try {
            String uniqueIds = "";
            for (int i = 0; i < cartItems.size(); i++) {
                uniqueIds = uniqueIds + "'" + cartItems.get(i).uniqueId + "',";
            }
            uniqueIds = uniqueIds.substring(0, uniqueIds.length() - 1);
            String query = "select distinct(Order_ID) as orderID from Order_items where Unique_id in ("+uniqueIds+") group by Order_ID;";
            List<String> orderIds = jdbcTemplate.query(query, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getString("orderID");
                }
            });
            return orderIds;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Menu> getFrequentItems(List<Cart> cartItems, List<String> orderIDS) {
        try {
            String uniqueIds = "", orderString = "";
            for (int i = 0; i < cartItems.size(); i++) {
                uniqueIds = uniqueIds + "'" + cartItems.get(i).uniqueId + "',";
            }
            for (int i = 0; i < orderIDS.size(); i++) {
                orderString = orderString + "'" + orderIDS.get(i) + "',";
            }
            uniqueIds = uniqueIds.substring(0, uniqueIds.length() - 1);
            orderString = orderString.substring(0, orderString.length() - 1);

            String query = "select count(Order_items.Unique_id) as frequency, Order_items.Item_name, M.Unique_id, M.Item_price " +
                    "from Order_items inner join Menu M on Order_items.Unique_id = M.Unique_id " +
                    "where Order_ID in (" + orderString +
                    "    ) AND Order_items.Unique_id NOT IN (" + uniqueIds +
                    "    ) group by Order_items.Unique_id order by frequency DESC ;";
            List<Menu> frequentItems = jdbcTemplate.query(query, new RowMapper<Menu>() {
                @Override
                public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
                    Menu item = new Menu();
                    item.itemName = resultSet.getString("Item_name");
                    item.itemPrice = resultSet.getInt("Item_price");
                    item.uniqueId = resultSet.getString("Unique_id");
                    return item;
                }
            });
            return frequentItems;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
