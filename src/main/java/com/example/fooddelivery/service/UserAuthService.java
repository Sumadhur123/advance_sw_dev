package com.example.fooddelivery.service;

import com.example.fooddelivery.models.User;
import com.example.fooddelivery.repository.actionInterface.UserActions;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;


@Service
public class UserAuthService {

	private UserActions userActions;
	private final static int totalUsernameSuggestions=3;
	private final static int random3digitNumber = 900;
	private final static int addToRandomNum = 100;
	
	public UserAuthService(UserActions userActions) {
		this.userActions = userActions;
	}

	public boolean registerUser(User user)  {
		try {
			String encryptedPwd = encryptPassword(user.getPassword());
			if (user.getUser_type().equals("admin")) {
				int hotelId = this.userActions.upsertHotel(user.getHotel_name());
				user.setHotel_id(hotelId);
			}
			
			this.userActions.insertIntoUser(user.getFull_name(), user.getUser_name(), user.getEmail(), encryptedPwd,
					user.getPincode(), user.getUser_type(), user.getHotel_id(), user.getBirthday());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String encryptPassword(String origPwd) {
		String encryptedpwd = "";
		for (int i = 0; i < origPwd.length(); i++) {
			char ch = origPwd.charAt(i);
			if (ch >= 'a' && ch <= 'z') {
				ch = (char) (ch + 5);
				if (ch > 'z')
					ch = (char) (ch - 'z' + 'a' - 1);
			} else if (ch >= 'A' && ch <= 'Z') {
				ch = (char) (ch + 5);
				if (ch > 'Z')
					ch = (char) (ch - 'Z' + 'A' - 1);
			} else if (ch >= '0' && ch <= '9') {
				ch = (char) (ch + 5);
				if (ch > '9')
					ch = (char) (ch - '9' + '0' - 1);
			}
			encryptedpwd = encryptedpwd + ch;
		}
		return encryptedpwd;

	}

	public boolean isCredentialsValid(String username, String password) {
		boolean validCreds = false;
		String userPwd = this.userActions.userPasswordfromDB(username);
		if (userPwd != null && !password.isEmpty()) {
			if (decryptPassword(userPwd).equals(password))
				validCreds = true;
		}
		return validCreds;
	}

	public String decryptPassword(String pwdfromDB) {
		String decryptedpwd = "";
		for (int i = 0; i < pwdfromDB.length(); i++) {
			char ch = pwdfromDB.charAt(i);
			if (ch >= 'a' && ch <= 'z') {
				ch = (char) (ch - 5);
				if (ch < 'a') {
					ch = (char) (ch + 'z' - 'a' + 1);
				}
			} else if (ch >= 'A' && ch <= 'Z') {
				ch = (char) (ch - 5);
				if (ch < 'A') {
					ch = (char) (ch + 'Z' - 'A' + 1);
				}
			} else if (ch >= '0' && ch <= '9') {
				ch = (char) (ch - 5);
				if (ch < '0') {
					ch = (char) (ch + '9' - '0' + 1);
				}	
			}
			decryptedpwd = decryptedpwd + ch;
		}
		return decryptedpwd;
	}

	public User getUserDetailsFromUsername(String name) {
		User user = this.userActions.getUserFromUsername(name);
		return user;
	}

	public boolean userAlreadyExists(String username) {
		boolean userExists = false;
		userExists = this.userActions.checkUserExists(username);
		return userExists;
	}


	public List<String> getSuggestedUsername(User user){
		List<String> listOfSuggesteduserNames=new ArrayList<String>();  
		String name = user.getFull_name();
		String username = user.getUser_name();
		Date dob = user.getBirthday();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dob);
		Integer birthdate = calendar.get(Calendar.DATE);
		Integer birthmonth = calendar.get(Calendar.MONTH);
		Integer birthyear = calendar.get(Calendar.YEAR);
		Random random = new Random();
		String suggestedname = null;
		String firstSuggestion=username+birthdate.toString()+birthmonth.toString();
		String secondSuggestion = name+birthyear.toString()+birthmonth.toString();
		if(!userAlreadyExists(firstSuggestion)) {
			listOfSuggesteduserNames.add(firstSuggestion);
		}
		if(!userAlreadyExists(secondSuggestion)) {
			listOfSuggesteduserNames.add(secondSuggestion);
		}
		int listsize = listOfSuggesteduserNames.size();	
		for(int i=listsize; i<totalUsernameSuggestions;i++) {
			Integer randomNumber = random.nextInt(random3digitNumber) + addToRandomNum;
			suggestedname = username+randomNumber.toString();
			listOfSuggesteduserNames.add(suggestedname);
		}
		return listOfSuggesteduserNames;
	}
}
