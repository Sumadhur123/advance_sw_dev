package com.example.fooddelivery.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.example.fooddelivery.repository.actionInterface.MembershipPointsActions;


@Component
public class MembershipPointsRepository implements MembershipPointsActions{

	private static final String updateMembershipPoints = "UPDATE  user set reward_points =? where user_name = ? ";
	private static final String getMembershipPoints = "SELECT reward_points from user where user_name = ? ";
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public int updateMembershipPoints(int membershipPoints, String username) {
		int recordsUpdated = 0;
		try {
			membershipPoints = membershipPoints+ getMembershipPoints(username);
			recordsUpdated = jdbcTemplate.update(updateMembershipPoints, membershipPoints, username);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}

		return recordsUpdated;
	}

	public int getMembershipPoints(String username) {
		int points = 0;

		try {
			points = jdbcTemplate.queryForObject(getMembershipPoints, Integer.class, new Object[] { username });
		} catch (EmptyResultDataAccessException | NullPointerException e) {
			e.printStackTrace();
		}
		return points;
	}

}
