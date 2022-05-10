package com.example.fooddelivery.controller;

import com.example.fooddelivery.service.MembershipPointsService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MembershipController {

	@Autowired
	private MembershipPointsService mempointservice;
	

	@GetMapping(value = { "/user/rewardpoints" })
	public String userSignIn(HttpSession request, Model model) {
		String name = (String) request.getAttribute("SESSION_USER");
		int mempoints = mempointservice.getMembershipPoints(name);
		model.addAttribute("rewardpoints", mempoints);
		return "user/rewardpoints";
	}

}
