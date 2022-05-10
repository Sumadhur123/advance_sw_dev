package com.example.fooddelivery.Service;

import com.example.fooddelivery.models.User;
import com.example.fooddelivery.repository.UserRepository;
import com.example.fooddelivery.repository.actionInterface.UserActions;
import com.example.fooddelivery.service.UserAuthService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class UserAuthTest {

  private static UserAuthService userAuthService;
  public static final String PASSWORDTOBEENCRYPTED = "aeshnav";
  public static final String ENCRYPTEDPASSWORD = "fjxmsfa";
  private static final String SAMPLEUSER = "SAMPLEUSER";
  private static final String SAMPLEUSERDOESNOTEXIST = "SAMPLEUSERDOESNOTEXIST";
  private static final String BIRTHDAY = "26/10/2000";

  @BeforeAll
  public static void init() throws Exception {
    UserActions userActions = Mockito.mock(UserRepository.class);
    Mockito.when(userActions.upsertHotel("Hotel")).thenReturn(1);
    Mockito.doThrow(Exception.class).when(userActions).insertIntoUser(null,"SomeUser",null,"mmm","Pincode", "user",1, null);
    Mockito.when(userActions.userPasswordfromDB(SAMPLEUSER)).thenReturn(ENCRYPTEDPASSWORD);
    Mockito.when(userActions.checkUserExists(SAMPLEUSER)).thenReturn(true);
    Mockito.when(userActions.checkUserExists(SAMPLEUSERDOESNOTEXIST)).thenReturn(false);
    Mockito.when(userActions.checkUserExists(SAMPLEUSERDOESNOTEXIST)).thenReturn(false);
    Mockito.when(userActions.checkUserExists(SAMPLEUSERDOESNOTEXIST)).thenReturn(false);
    userAuthService = new UserAuthService(userActions);
  }

  @Test
  void registerSuccessfully() {
    User user = new User();
    user.setUser_name("User");
    user.setUser_type("user");
    user.setPincode("Pincode");
    user.setPassword("hhh");
    user.setHotel_id(1);
    boolean userAdded = userAuthService.registerUser(user);
    assertTrue(userAdded);
  }

  @Test
  void dbExceptionInRegister() {
    User user = new User();
    user.setUser_name("SomeUser");
    user.setUser_type("user");
    user.setPincode("Pincode");
    user.setPassword("hhh");
    user.setHotel_id(1);
    boolean userAdded = userAuthService.registerUser(user);
    assertFalse(userAdded);
  }
  
  
  @Test
  void passwordEncrytionSucessfull() {
	  String encyptedPasswordResult = userAuthService.encryptPassword(PASSWORDTOBEENCRYPTED);
	  assertEquals(ENCRYPTEDPASSWORD, encyptedPasswordResult );
  }
  
  @Test
  void passwordEncrytionUnsucessfull() {
	  String encyptedPasswordResult = userAuthService.encryptPassword(PASSWORDTOBEENCRYPTED);
	  assertNotSame(ENCRYPTEDPASSWORD, encyptedPasswordResult ); 
  }
  
  @Test
  void passwordDecrytionSucessfull() {
	  String decryptedPasswordResult = userAuthService.decryptPassword(ENCRYPTEDPASSWORD);
	  assertEquals(PASSWORDTOBEENCRYPTED, decryptedPasswordResult );
  }
  
  @Test
  void passwordDecrytionUnsucessfull() {
	  String decryptedPasswordResult = userAuthService.decryptPassword(ENCRYPTEDPASSWORD);
	  assertNotSame(PASSWORDTOBEENCRYPTED, decryptedPasswordResult ); 
  }
  
  @Test
  void isCredentialsValidSuccessful() {
	  boolean isCredsValid = userAuthService.isCredentialsValid(SAMPLEUSER,PASSWORDTOBEENCRYPTED);
	  assertTrue(isCredsValid);
  }
  
  @Test
  void isCredentialsValidUnsucessful() {
	  boolean isCredsValid = userAuthService.isCredentialsValid(SAMPLEUSER,ENCRYPTEDPASSWORD);
	  assertFalse(isCredsValid);
  }
  
  @Test
  void userAlreadyExistsSuccess() {
	  boolean userExists = userAuthService.userAlreadyExists(SAMPLEUSER);
	  assertTrue(userExists);
  }
  
  @Test
  void userAlreadyExistsUnsucessful() {
	  boolean userDoesnotExists = userAuthService.userAlreadyExists(SAMPLEUSERDOESNOTEXIST);
	  assertFalse(userDoesnotExists);
  }
  
  @Test
  void getSuggestedUserNamesUnsuccess() throws ParseException {
	  List<String> listOfSuggesteduserNames=new ArrayList<String>();  
	  User user = new User();
	  DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	  Date date = format.parse("26/10/2000");
	  user.setBirthday(date);
	  user.setUser_name(SAMPLEUSER);
	  user.setFull_name(SAMPLEUSER);
	  listOfSuggesteduserNames = userAuthService.getSuggestedUsername(user);
	  List<String> listOfSuggetions = asList(SAMPLEUSER, SAMPLEUSER, SAMPLEUSER);
	  boolean test=false;
	  for(String suggestedUsername:listOfSuggetions) {
		  test = Pattern.matches("(?s).*\\bSAMPLEUSER\\b.*", suggestedUsername);
		 assertTrue(test);

	  }
  }
  
}
