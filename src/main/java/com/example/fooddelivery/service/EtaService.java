package com.example.fooddelivery.service;

import java.time.LocalTime;

import com.example.fooddelivery.repository.LinkRepository;
import com.example.fooddelivery.repository.MenuRepository;
import org.springframework.stereotype.Service;


@Service
public class EtaService {

    private LinkRepository linkRepository;
    private DeliveryChargesService deliveryChargesService;

    public EtaService(LinkRepository linkRepository, DeliveryChargesService deliveryChargesService){
        this.linkRepository=linkRepository;
        this.deliveryChargesService=deliveryChargesService;
    }

    public int calculateTimeFactor(LocalTime time){
        int hour = time.getHour();

        if(hour>8 && hour<=11){
            return 2;
        }
        else if(hour>11 && hour<=14){
            return 8;
        }
        else if(hour>14 && hour<=19){
            return 5;
        }
        else if(hour>19 && hour<=22){
            return 10;
        }
        else if(hour>22 && hour<=8){
           return 3;
        }
        else{
            return 0;
        }
    }

    public int CalculateTrafficFactor(LocalTime time){
        int hour = time.getHour();


        if(hour>8 && hour<=11){
            return 8;
        }
        else if(hour>11 && hour<=14){
            return 6;
        }
        else if(hour>14 && hour<=19){
            return 7;
        }
        else if(hour>19 && hour<=22){
            return 10;
        }
        else if(hour>22 && hour<=8){
            return 3;
        }
        else{
            return 0;
        }

    }

    public int CalculateDistanceFactor(String sourcePincode,String destinationPincode){
        int distance = deliveryChargesService.getDistance(sourcePincode,destinationPincode);
        if(distance==0){
            return 0;
        }
        else if(distance<=10){
            return 3;
        }
        else if(distance>10 && distance<=15){
            return 5;
        }
        else{
            return 7;
        }


    }


    public int CalculateEta(int restaurantId, String userName, LocalTime time){

        String sourcePincode=linkRepository.getSourcePincode(restaurantId);
        String destinationPincode=linkRepository.getDestinationPincode(userName);
        int costOfTime= calculateTimeFactor(time);

        int costOfTraffic= CalculateTrafficFactor(time);

        int costOfDistance=CalculateDistanceFactor(sourcePincode,destinationPincode);
        int costOfTimeAndTraffic=costOfTime+costOfTraffic;
        int costOfTimeTrafficAndDistance=costOfTimeAndTraffic+costOfDistance;

        if(costOfTimeTrafficAndDistance<=6){
            return 5; }
        else if(costOfTimeTrafficAndDistance>=7 && costOfTimeTrafficAndDistance <=12){
            return 10;
        }
        else if(costOfTimeTrafficAndDistance>=13 && costOfTimeTrafficAndDistance<=18){
            return 15;
        }
        else if(costOfTimeTrafficAndDistance>=19 && costOfTimeTrafficAndDistance<=24){
            return 20;
        }
        else if(costOfTimeTrafficAndDistance>=25 && costOfTimeTrafficAndDistance<=30){
            return 25;
        }
       else{
           return 0;
        }
    }

}
