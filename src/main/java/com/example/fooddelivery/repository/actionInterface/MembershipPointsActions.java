package com.example.fooddelivery.repository.actionInterface;

public interface MembershipPointsActions {
	
	  int updateMembershipPoints(int membershipPoints, String username);
	  int getMembershipPoints(String username);
}
