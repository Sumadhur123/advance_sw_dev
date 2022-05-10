package com.example.fooddelivery.Service;
import com.example.fooddelivery.models.Link;

import com.example.fooddelivery.repository.LinkRepository;
import com.example.fooddelivery.service.DeliveryChargesService;
import com.example.fooddelivery.service.EtaService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class EtaServiceTest {

    private static EtaService etaService;
    private static final String sourcePincode="B3H 1C4";
    private static final String destinationPincode="B3H 2A5";
    private static final int link = 10;
    static Link link1 = new Link(sourcePincode,destinationPincode,link);
    @BeforeAll
    public static void init(){
        DeliveryChargesService deliveryChargesService=Mockito.mock(DeliveryChargesService.class);
        Mockito.when(deliveryChargesService.getDistance(sourcePincode,destinationPincode)).thenReturn(10);
        LinkRepository linkRepository = Mockito.mock(LinkRepository.class);
        List<Link> fetchlink = new ArrayList<>();
        fetchlink.add(link1);
        Mockito.when(linkRepository.getLinks()).thenReturn(fetchlink);
        etaService = new EtaService(linkRepository,deliveryChargesService);
    }

    @Test
    void calculateTimeFactorTest(){

        LocalTime time = LocalTime.now();
        assertEquals(etaService.calculateTimeFactor(time),10);
    }

    @Test
    void CalculateTrafficFactorTest(){
        LocalTime time = LocalTime.now();
        assertEquals(etaService.CalculateTrafficFactor(time),10);
    }

    @Test
    void CalculateDistanceFactorTest(){
        String sourcePincode="B3H 1C4";
        String destinationPincode="B3H 2A5";
        assertEquals(etaService.CalculateDistanceFactor(sourcePincode,destinationPincode),3);

    }


}
