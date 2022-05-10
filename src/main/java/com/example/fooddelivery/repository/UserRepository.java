package com.example.fooddelivery.repository;

import com.example.fooddelivery.models.User;
import com.example.fooddelivery.repository.actionInterface.UserActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class UserRepository implements UserActions {

	private static final String addUser = "INSERT INTO user (full_name, user_name, email, password, pincode,user_type, hotel_id, birthday) VALUES (?,?,?,?,?,?,?,?)";
	private static final String addHotel = "INSERT INTO hotels (hotel_name) VALUES (?)";
	private static final String getHotelId = "select id from hotels where hotel_name = ?";
	private static final String getPassword = "SELECT password from user where user_name = ? ";

	private static final String getDob = "SELECT birthday from user where user_name = ? ";
	private static final String getuserWithName = "SELECT id, user_name, email, user_type, hotel_id from user where user_name = ? ";
	private static final String checkIfUsernameExists = "SELECT EXISTS(SELECT 1 FROM user WHERE user_name = ?)";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insertIntoUser(String fullName, String userName, String email, String password, String pincode,
			String user_type, int hotelId, Date birthday) throws Exception {
		jdbcTemplate.update(addUser, fullName, userName, email, password, pincode, user_type, hotelId, birthday);
	}

	public int upsertHotel(String hotel_name) {
		int hotelId;
		try {
			jdbcTemplate.update(addHotel, hotel_name);
		} catch (DuplicateKeyException e) {
		} finally {
			hotelId = jdbcTemplate.query(getHotelId, new Object[] { hotel_name }, (result) -> {
				result.next();
				return result.getInt("id");
			});
		}
		return hotelId;
	}

	public String userPasswordfromDB(String username) {
		String userPassword = null;

		try {
			userPassword = jdbcTemplate.queryForObject(getPassword, String.class, username);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return userPassword;
	}



	public Date getDobFromDB(String username) {
		Date dob = null;
		try {
			dob = jdbcTemplate.queryForObject(getDob, Date.class, username);
		} catch (EmptyResultDataAccessException | NullPointerException e) {
			e.printStackTrace();
		}
		return dob;
	}

	public User getUserFromUsername(String name) {
		User user = null;
		try {
			user = jdbcTemplate.query(getuserWithName, new Object[] { name }, (resultSet) -> {
				resultSet.next();
				User details = new User();
				details.setId(resultSet.getInt("id"));
				details.setUser_name(resultSet.getString("user_name"));
				details.setEmail(resultSet.getString("email"));
				details.setUser_type(resultSet.getString("user_type"));
				details.setHotel_id(resultSet.getInt("hotel_id"));
				return details;
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public boolean checkUserExists(String username) {
		return jdbcTemplate.queryForObject(checkIfUsernameExists, Boolean.class, username);
	}
}
