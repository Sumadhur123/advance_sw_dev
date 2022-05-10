package com.example.fooddelivery.service;


import com.example.fooddelivery.repository.actionInterface.UserActions;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BdayDiscountService {

    private UserActions userActions;

    public BdayDiscountService(UserActions userActions) {
        this.userActions = userActions;
    }

    public Date getDobFromName(String username){
        Date dob = this.userActions.getDobFromDB(username);
        return dob;
    }
}
