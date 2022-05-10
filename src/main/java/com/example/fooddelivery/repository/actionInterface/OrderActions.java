package com.example.fooddelivery.repository.actionInterface;

public interface OrderActions {
	  int getTotalOrders(String username, String orderStatus);
	  double getTotalOrderAmount(String username, String orderStatus);
	
}
