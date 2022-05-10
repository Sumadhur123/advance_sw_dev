package com.example.fooddelivery.repository;

import com.example.fooddelivery.models.DeliveryPerson;
import com.example.fooddelivery.models.Order;
import com.example.fooddelivery.repository.actionInterface.DeliveryAssignActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DeliveryAssignRepository implements DeliveryAssignActions {

    private static final String getDeliveryPersonList1="SELECT * FROM delivery_person WHERE hotel_id=";
    private static final String getDeliveryPersonList2=" ORDER BY total_orders ASC";
    private static final String addToSchedule="INSERT INTO schedule(deliveryperson_id, order_id, start_time, end_time) VALUES (?,?,?,?)";
    private static final String getOrder = "SELECT * FROM Customer_Order WHERE Order_ID=";
    private static final String incrementTotalOrders = "UPDATE delivery_person SET total_orders=total_orders+1 WHERE id=?";
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public DeliveryPerson getDeliveryPersonFromList(int restaurantId){
        List<DeliveryPerson> deliveryPeople = new ArrayList<>();
        jdbcTemplate.query(getDeliveryPersonList1 + restaurantId + getDeliveryPersonList2 + ";", (resultSet -> {
            DeliveryPerson person = new DeliveryPerson();
            getPerson(person, resultSet);
            deliveryPeople.add(person);
        }));
        return deliveryPeople.get(0);
    }

    public Boolean addToSchedule(int deliveryPersonId, String orderId){
        Order order = new Order();
        jdbcTemplate.query(getOrder + "'"+ orderId + "'" + ";", (resultSet -> {
            getOrder(order,resultSet);
        }));

        int eta = order.getEta();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTime = order.getOrderDate() + " " +order.getOrderTime();
        LocalDateTime startDateTime = LocalDateTime.parse(startTime,dtf);
        LocalDateTime endDateTime = startDateTime.plusMinutes(eta);
        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);
        int success = jdbcTemplate.update(addToSchedule, deliveryPersonId, orderId,startTimestamp, endTimestamp);
        return success!=0;
    }
    public void incrementTotalOrders(DeliveryPerson person) {
        jdbcTemplate.update(incrementTotalOrders, person.getId());
    }
    private void getPerson(DeliveryPerson person, ResultSet resultSet) throws SQLException {
        person.setId(resultSet.getInt("id"));
        person.setName(resultSet.getString("name"));
        person.setRestaurant_ID(resultSet.getInt("hotel_id"));
        person.setOrder_id(resultSet.getString("Order_ID"));
        person.setP_number(resultSet.getString("p_number"));
    }
    private void getOrder(Order order, ResultSet resultSet) throws SQLException {
        order.setOrder_Id(resultSet.getString("Order_ID"));
        order.setRestaurant_ID(resultSet.getInt("Restaurant_ID"));
        order.setOrderDate(resultSet.getString("order_date"));
        order.setOrderTime(resultSet.getString("order_time"));
        order.setEta(resultSet.getInt("ETA"));
    }
}
