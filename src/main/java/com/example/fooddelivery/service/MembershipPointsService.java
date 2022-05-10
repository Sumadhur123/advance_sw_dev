package com.example.fooddelivery.service;


import com.example.fooddelivery.repository.MembershipPointsRepository;
import com.example.fooddelivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipPointsService {
	
	private static final String orderStatusPlaced = "Placed";
	private static final String orderStatusCompleted = "Completed";
	private static final int rewardPointsFirstThreshold = 150;
	private static final int rewardPointsSecondThreshold = 500;
	private static final double tenPercent = 0.10;
	private static final double twentyPercent = 0.20;
	
	@Autowired
	MembershipPointsRepository mempointdao;
	
	@Autowired
	OrderRepository orderdao;

	
	public MembershipPointsService(MembershipPointsRepository mempointdao, OrderRepository orderdao) {
		this.mempointdao = mempointdao;
		this.orderdao = orderdao;
	}	
	
	public int calcMemberShipPoints(String username) {
		int membershipPoints = 0;
		int noOfOrders = orderdao.getTotalOrders(username, orderStatusCompleted);
		double amountOfOrders = orderdao.getTotalOrderAmount(username, orderStatusPlaced);
		membershipPoints = (int) (Math.floor(amountOfOrders) * .10 + noOfOrders * 10);
		updateMembershipPoints(username, membershipPoints);
		return membershipPoints;

	}

	public double calcDiscountBasedOnMemPoints(String username, int orderAmount) {
		double membershipDiscount=0;
		int mempoints = getMembershipPoints(username);
		if(mempoints>=rewardPointsFirstThreshold && mempoints<rewardPointsSecondThreshold)
			membershipDiscount = orderAmount*tenPercent;
		if(mempoints>=rewardPointsSecondThreshold)
			membershipDiscount = orderAmount*twentyPercent;
		return membershipDiscount;		
	}
	
	public void updateMembershipPoints(String username, int membershipPoints) {
		mempointdao.updateMembershipPoints(membershipPoints, username);
	}

	public int getMembershipPoints(String username) {
		int points = mempointdao.getMembershipPoints(username);
		return points;
	}

}
