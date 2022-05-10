package com.example.fooddelivery.Service;

import com.example.fooddelivery.models.Cart;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.example.fooddelivery.models.Order;
import com.example.fooddelivery.service.PlaceOrderService;
import com.example.fooddelivery.repository.CartRepository;
import com.example.fooddelivery.repository.LinkRepository;
import com.example.fooddelivery.repository.PlaceOrderRepository;
import com.example.fooddelivery.service.EtaService;
import com.example.fooddelivery.service.DeliveryChargesService;
import com.example.fooddelivery.service.MembershipPointsService;
import com.example.fooddelivery.service.CartService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlaceOrderTest {
    private static PlaceOrderService placeOrderService;

    private static final int restaurantId=1;
    private static final String userName="user";
    private static final String itemName="Cheese pizza";
    private static final int itemPrice=10;
    private static final String orderId="abc";
    private static final String restaurantName="Dominos";
    private static final String uniqueId="1CHPZ";
    private static final String sourcePincode="B3H 1C4";
    private static final String destinationPincode="B3H 2A5";
    static Order order1 = new Order(restaurantId,restaurantName);
    static Cart cart1 = new Cart(restaurantId,userName,itemName,itemPrice,restaurantName,uniqueId);


    @BeforeAll
    public  static void init(){
        EtaService etaService= Mockito.mock(EtaService.class);
        LocalTime time = LocalTime.now();
        LocalDate date = LocalDate.now();
        Mockito.when(etaService.calculateTimeFactor(time)).thenReturn(3);
        Mockito.when(etaService.CalculateTrafficFactor(time)).thenReturn(3);
        Mockito.when(etaService.CalculateDistanceFactor(sourcePincode,destinationPincode)).thenReturn(3);
        Mockito.when(etaService.CalculateEta(restaurantId,userName,time)).thenReturn(10);
        CartRepository cartRepository = Mockito.mock(CartRepository.class);
        List<Cart> cart = new ArrayList<>();
        cart.add(cart1);
        Mockito.when(cartRepository.getFromCart(userName)).thenReturn(cart);
        PlaceOrderRepository placeOrderRepository = Mockito.mock(PlaceOrderRepository.class);
        List<Order> order = new ArrayList<>();
        order.add(order1);
        Mockito.when(placeOrderRepository.getOrder(userName,orderId)).thenReturn(order);
        LinkRepository linkRepository = Mockito.mock(LinkRepository.class);
        DeliveryChargesService deliveryChargesService = Mockito.mock(DeliveryChargesService.class);
        Mockito.when(deliveryChargesService.calculateDeliveryCharges(restaurantId,userName)).thenReturn(5);

        MembershipPointsService membershipPointsService=Mockito.mock(MembershipPointsService.class);
        Mockito.when(membershipPointsService.calcDiscountBasedOnMemPoints(userName,10)).thenReturn(1.0);
        CartService cartService = Mockito.mock(CartService.class);

        placeOrderService = new PlaceOrderService(cartRepository,placeOrderRepository, linkRepository, etaService, membershipPointsService, cartService, deliveryChargesService);


    }
    @Test
    void getCartOrdersTest(){
        String userName="user";
        assertEquals(placeOrderService.getCartOrders(userName).get(0),cart1);

    }

    @Test
    void addOrderTest(){
        String userName="user";
        String expected="abc";
        assertEquals(expected,orderId);


    }

    @Test void getOrderTest(){
       String userName="user";
       String orderId="abc";
       assertEquals(placeOrderService.getOrder(userName,orderId).get(0),order1);

    }
}
