package com.example.fooddelivery.repository;

import com.example.fooddelivery.repository.actionInterface.OrderActions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderRepository implements OrderActions {


  private static final String totalOrders = "SELECT count(*) from Customer_Order where user_name = ? and order_status = ? ";
  private static final String totalAmount = "SELECT sum(Total_amount) from Customer_Order where user_name = ? and order_status = ? ";

  
  @Autowired
  private JdbcTemplate jdbcTemplate;

  
	public int getTotalOrders(String username, String orderStatus){
		Integer noOfOrders = 0;
		
		try {
			noOfOrders = jdbcTemplate.queryForObject(totalOrders,Integer.class, new Object[] {username, orderStatus});
		}
		catch(EmptyResultDataAccessException e){
			e.printStackTrace();
		}
		return noOfOrders;
	}
	
	public double getTotalOrderAmount(String username, String orderStatus){
		double totalAmountOfOrders = 0.0;
		
		try {
			totalAmountOfOrders = jdbcTemplate.queryForObject(totalAmount,Float.class, new Object[] {username, orderStatus});
		}
		catch(EmptyResultDataAccessException e){
			e.printStackTrace();
		}
		return totalAmountOfOrders;
	} 
	

}
