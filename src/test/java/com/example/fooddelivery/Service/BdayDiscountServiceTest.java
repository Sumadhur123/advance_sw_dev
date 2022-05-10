package com.example.fooddelivery.Service;

import com.example.fooddelivery.models.User;
import com.example.fooddelivery.repository.UserRepository;
import com.example.fooddelivery.repository.actionInterface.UserActions;
import com.example.fooddelivery.service.BdayDiscountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BdayDiscountServiceTest {

    private static BdayDiscountService bdayDiscountService;
    private static Date date = new Date();

    @BeforeAll
    public static void init() {
        UserActions userActions = Mockito.mock(UserRepository.class);
        Mockito.when(userActions.getDobFromDB("User")).thenReturn(date);
        bdayDiscountService = new BdayDiscountService(userActions);
    }

    @Test
    void getDobFromNameSuccess() {
        User user = new User();
        user.setUser_name("User");
        user.setUser_type("user");
        user.setPassword("hhh");
        user.setBirthday(date);
        Date dob = bdayDiscountService.getDobFromName(user.getUser_name());
        assertNotNull(dob);
    }

    @Test
    void getDobFromNameFail() {
        User user = new User();
        user.setUser_name("User2");
        user.setUser_type("user");
        user.setPassword("hhh");
        user.setBirthday(null);
        Date dob = bdayDiscountService.getDobFromName(user.getUser_name());
        assertNull(dob);
    }
}