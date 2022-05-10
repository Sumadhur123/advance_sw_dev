package com.example.fooddelivery.repository;
import com.example.fooddelivery.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.example.fooddelivery.repository.actionInterface.PlaceOrderActions;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Component
public class PlaceOrderRepository implements PlaceOrderActions{

    private static final String removeFromCart = "DELETE FROM Cart WHERE User_name= ?";
    private static final String addToOrder="INSERT INTO `Customer_Order`(Order_ID,User_name,Restaurant_ID,Restaurant_Name,Total_amount,coupon_discount,delivery_charges,order_status,order_date,order_time,ETA) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String addToOrderItems="INSERT INTO Order_items(Order_ID,Item_Name,Item_price,Unique_ID) VALUES(?,?,?,?)";
    private static final String getOrder="SELECT * FROM `Customer_Order` WHERE User_name=";
    private static final String condition1=" AND Order_ID=";
    private static final String getOrderRestaurant="Select Restaurant_ID FROM Customer_Order WHERE Order_ID=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void removeFromCart(String userName){
        jdbcTemplate.update(removeFromCart,userName);
    }

    public void addToOrders(String orderId, String userName, int restaurantId, String restaurantName, double totalAmount, double discount,int deliveryCharges, String orderStatus, LocalDate orderDate, LocalTime orderTime,int ETA){
        jdbcTemplate.update(addToOrder,orderId,userName,restaurantId,restaurantName,totalAmount,discount,deliveryCharges, orderStatus,orderDate,orderTime,ETA);

    }

    public void addToOrderItems(String orderID,String itemName,int itemPrice,String uniqueID){
        jdbcTemplate.update(addToOrderItems,orderID,itemName,itemPrice,uniqueID);

    }

    public List<Order> getOrder(String userName, String orderID){
        return jdbcTemplate.query(getOrder + "'" + userName + "'"+condition1+"'"+orderID+"';", new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                Order o = new Order();
                o.setDiscount(rs.getDouble("coupon_discount"));
                o.setOrder_Id(rs.getString("Order_ID"));
                o.setUserName(rs.getString("User_name"));
                o.setRestaurantName(rs.getString("Restaurant_Name"));
                o.setTotalAmount(rs.getInt("Total_amount"));
                o.setEta(rs.getInt("ETA"));
                return o;
            }
        });
    }

    public int getOrderRestaurant(String orderId){
        String rId = (String) jdbcTemplate.queryForObject(getOrderRestaurant,new Object[]  {orderId} , String.class);
        assert rId != null;
        return   Integer.parseInt(rId);
    }

}
