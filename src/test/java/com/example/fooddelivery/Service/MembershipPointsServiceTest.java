package com.example.fooddelivery.Service;

import com.example.fooddelivery.repository.MembershipPointsRepository;
import com.example.fooddelivery.repository.OrderRepository;
import com.example.fooddelivery.service.MembershipPointsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;


public class MembershipPointsServiceTest {

    private static MembershipPointsService membershipPointsService;	
    private static final String SAMPLEUSER = "SAMPLEUSER";
    private static final String ORDERSTATUS = "Completed";
    private static final int TOTALORDERS = 50;
    private static final double TOTALORDERAMOUNT = 110.0;
    private static final int EXPECTEDMEMPOINTSCALC = 500;
    
    @BeforeAll
    public static void init() {
        MembershipPointsRepository membershipdao = Mockito.mock(MembershipPointsRepository.class);
        OrderRepository orderdao = Mockito.mock(OrderRepository.class);
        Mockito.when(membershipdao.updateMembershipPoints(4, SAMPLEUSER)).thenReturn(1);
        Mockito.when(membershipdao.getMembershipPoints(SAMPLEUSER)).thenReturn(600);
        Mockito.when(orderdao.getTotalOrders(SAMPLEUSER,ORDERSTATUS)).thenReturn(TOTALORDERS);
        Mockito.when(orderdao.getTotalOrderAmount(SAMPLEUSER,ORDERSTATUS)).thenReturn(TOTALORDERAMOUNT); 
        membershipPointsService = new MembershipPointsService(membershipdao,orderdao);
        
    }

 
    @Test
    void calcMemberShipPointsSuccess() {
    	int actualMemberPointResult = membershipPointsService.calcMemberShipPoints(SAMPLEUSER);
    	assertEquals(EXPECTEDMEMPOINTSCALC,actualMemberPointResult );
    }
    
    @Test
    void calcMemberShipPointsUnsuccessful() {
    	int actualMemberPointResult = membershipPointsService.calcMemberShipPoints(SAMPLEUSER);
    	assertNotSame(1000,actualMemberPointResult );
    }
    
   @Test
    void calcDiscountBasedOnMemPointsSuccess() {
    	double amountActual = membershipPointsService.calcDiscountBasedOnMemPoints(SAMPLEUSER, 600);
    	assertEquals(120,amountActual );
    }
    
    @Test
    void calcDiscountBasedOnMemPointsUnsuccessful() {
    	double amountActual = membershipPointsService.calcDiscountBasedOnMemPoints(SAMPLEUSER, 250);
    	assertNotSame(60,amountActual);
    }
    
    @Test
    void getMembershipPointsTest() {
    	int points = membershipPointsService.getMembershipPoints(SAMPLEUSER);
    	assertEquals(600,points );
    }
    
    @Test
    void updateMembershipPointsTest() {
    	membershipPointsService.updateMembershipPoints(SAMPLEUSER, 4);
    	int points = membershipPointsService.getMembershipPoints(SAMPLEUSER);
    	assertEquals(600,points );
    }
}
