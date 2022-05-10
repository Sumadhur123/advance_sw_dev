package com.example.fooddelivery.repository.actionInterface;
import com.example.fooddelivery.models.Order;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface PlaceOrderActions {

    public void removeFromCart(String userName);
    public void addToOrders(String orderId, String userName, int restaurantId, String restaurantName, double totalAmount, double discount,int deliveryCharges, String orderStatus, LocalDate orderDate, LocalTime orderTime, int ETA);
    public void addToOrderItems(String orderID,String itemName,int itemPrice,String uniqueID);
    public List<Order> getOrder(String userName, String orderID);

}
