package com.example.fooddelivery.Service;

import com.example.fooddelivery.models.Link;
import com.example.fooddelivery.repository.LinkRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.fooddelivery.service.DeliveryChargesService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class DeliveryChargeTest {

    private static DeliveryChargesService deliveryChargesService;
    private static final String sourcePincode="B3H 1C4";
    private static final String destinationPincode="B3H 2A5";
    private static final int link = 10;
    private static final int restaurantId=1;
    private static final String userName="user";
    static Link link1 = new Link(sourcePincode,destinationPincode,link);

    @BeforeAll
    public static void init() {
        LinkRepository linkRepository= Mockito.mock(LinkRepository.class);
        List<Link> fetchlink = new ArrayList<>();
        fetchlink.add(link1);
        Mockito.when(linkRepository.getLinks()).thenReturn(fetchlink);
        deliveryChargesService = new DeliveryChargesService(linkRepository);
        Mockito.when(linkRepository.getSourcePincode(restaurantId)).thenReturn("B3H 1C4");
        Mockito.when(linkRepository.getDestinationPincode(userName)).thenReturn("B3H 2A5");
    }

    @Test
    void getDistance(){
        String sourcePincode="B3H 1C4";
        String destinationPincode="B3H 2A5";
        assertEquals(deliveryChargesService.getDistance(sourcePincode,destinationPincode),10);
    }


    @Test
    void calculateDeliveryCharges(){
        int restaurantId=1;
        String userName="user";
        assertEquals(deliveryChargesService.calculateDeliveryCharges(restaurantId,userName),5);

    }
}
