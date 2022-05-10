package com.example.fooddelivery.repository.actionInterface;

import java.util.Date;

import com.example.fooddelivery.models.User;

public interface UserActions {
	
  void insertIntoUser(String full_name, String user_name, String email, String password, String pincode, String user_type, int hotelId, Date birthday) throws Exception;
  int upsertHotel(String hotelName);
  String userPasswordfromDB(String username);
  Date getDobFromDB(String username);
  User getUserFromUsername(String name);
  boolean checkUserExists(String username);
}
